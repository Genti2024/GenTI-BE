package com.gt.genti.domain.enums.converter;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.gt.genti.domain.enums.ConvertableEnum;
import com.gt.genti.error.DynamicException;
import com.gt.genti.error.ExpectedException;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultStringAttributeConverter<T extends Enum<T> & ConvertableEnum>
	implements AttributeConverter<T, String> {
	private final Class<T> enumClassType;

	@Override
	public String convertToDatabaseColumn(T attribute) {
		try{
			return attribute.getStringValue();
		} catch (NullPointerException e){
			if(attribute.isNullable()){
				return null;
			} else{
				throw new DynamicException("ENUM", enumClassType.getName() + " 타입은 null 값을 허용하지 않습니다.", HttpStatus.BAD_REQUEST);
			}
		}
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return EnumUtil.stringToEnum(getEnumClassType(), dbData);
	}
}
