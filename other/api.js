GET 주문수락가능여부조회
request

response
{
    success : true
    response : {
        receiving-requests : true/false
    }
    error : null
}

POST 주문수락가능여부변경
request
{
    receiving-requests : true
}
response
{
    success : true
    response : {
        receiving-requests : true/false
    }
    error : null
}

POST 주문수락

response
{
    success : true
    response : null
    error : null
}

GET 주문조회

response
{
    success : true
    response : {
        id : 1
        prompt : "이쁘게요"
        facePictureList : [
            {
                url : "cloudfront"
            },
            {
                url : "cloudfront"
            },
            {
                url : "cloudfront"
            }
        ]
        posePictureUrl : "cloudfront"
        createAt : timestamp(년월일)
        modifiedAt : timestamp(년월일)
    }
    error : null
}