package com.gt.genti.config;

import static com.gt.genti.response.GentiResponse.*;
import static org.springframework.util.MimeTypeUtils.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.swagger.EnumResponseGroup;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.swagger.RequireImageUpload;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SwaggerEnumOperationCustomizer implements OperationCustomizer {

	@SuppressWarnings("rawtypes")
	private final Schema errorEntitySchema = ModelConverters.getInstance()
		.readAllAsResolvedSchema(ApiResult.class).schema;

	@Override
	public Operation customize(Operation operation, HandlerMethod handlerMethod) {
		ApiResponses apiResponses = operation.getResponses();
		apiResponses.clear();
		Type dtoType = getActualType(handlerMethod.getReturnType().getGenericParameterType());
		addAdditionalDescriptionIfHasCustomAnnotation(operation, handlerMethod);
		EnumResponses apiResponseCodes = handlerMethod.getMethodAnnotation(EnumResponses.class);
		if (apiResponseCodes != null) {
			Arrays.stream(apiResponseCodes.value()).forEach(
				code -> putApiResponseCode(apiResponses, code.value(), dtoType)
			);

			Arrays.stream(apiResponseCodes.groups()).forEach(
				group -> putApiResponseCodeGroup(apiResponses, group, dtoType)
			);
		}

		return operation;
	}

	private void addAdditionalDescriptionIfHasCustomAnnotation(Operation operation, HandlerMethod handlerMethod) {
		if (handlerMethod.getMethod().isAnnotationPresent(RequireImageUpload.class)) {
			RequireImageUpload requireImageUpload = handlerMethod.getMethod().getAnnotation(RequireImageUpload.class);
			operation.setDescription(
				requireImageUpload.description() + "<br/>"
					+ operation.getDescription());
		}
	}

	private void putApiResponseCodeGroup(ApiResponses apiResponses, EnumResponseGroup group, Type dtoType) {
		try {
			Arrays.stream(EnumResponseGroup.class.getField(group.name())
				.getAnnotation(EnumResponses.class).value()).forEach(apiResponseCode -> {
				putApiResponseCode(apiResponses, apiResponseCode.value(), dtoType);
			});
		} catch (NoSuchFieldException e) {
			throw ExpectedException.withLogging(ResponseCode.UnHandledException, e.getMessage());
		}
	}

	private void putApiResponseCode(ApiResponses apiResponses, ResponseCode code, Type dtoType) {
		if (code.isSuccess()) {
			apiResponses.put(code.toString(), convertDataResponse(code, dtoType));

		} else {
			apiResponses.put(code.toString(), convertErrorResponse(code));
		}
	}

	private ApiResponse convertErrorResponse(ResponseCode code) {
		return convertResponseInner(
			errorEntitySchema.description(code.getErrorMessage()),
			code,
			error(code)
		);
	}

	private ApiResponse convertDataResponse(ResponseCode code, Type dtoType) {
		return convertResponseInner(
			customizeSchema(code, dtoType),
			code
		);
	}

	@SuppressWarnings("rawtypes")
	private Schema customizeSchema(ResponseCode responseCode, Type dtoType) {
		Schema schema = ModelConverters.getInstance().readAllAsResolvedSchema(dtoType).schema;
		Map<String, Schema> properties = schema.getProperties();
		Boolean success = responseCode.isSuccess();
		String errorCode = responseCode.getErrorCode();
		String errorMessage = responseCode.getErrorMessage();

		properties.get("success").setDefault(success);
		properties.get("errorCode").setDefault(errorCode);
		properties.get("errorMessage").setDefault(errorMessage);

		return schema;
	}

	private ApiResponse convertResponseInner(
		@SuppressWarnings("rawtypes") Schema schema, ResponseCode code) {
		return convertResponseInner(schema, code, null);
	}

	private ApiResponse convertResponseInner(
		@SuppressWarnings("rawtypes") Schema schema, ResponseCode code, ResponseEntity<ApiResult<?>> example) {
		MediaType mediaType = new MediaType()
			.schema(schema);

		if (example != null) {
			mediaType.addExamples(code.name(), new Example().value(example));
		}

		return new ApiResponse()
			.content(
				new Content()
					.addMediaType(
						APPLICATION_JSON_VALUE,
						mediaType
					)
			)
			.description(code.getErrorMessage());
	}

	private Type getActualType(Type type) {
		if (type instanceof ParameterizedType parameterizedType) {
			return parameterizedType.getActualTypeArguments()[0];
		}
		return type;
	}

}