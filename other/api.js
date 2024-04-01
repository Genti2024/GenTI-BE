// GET 주문수락가능여부조회
// request
//
// response
// {
//     success : true
//     response : {
//         receivingRequests : true/false
//     }
//     error : null
// }
//
// POST 주문수락가능여부변경
// request
// {
//     receivingRequests : true
// }
// response
// {
//     success : true
//     response : {
//         receivingRequests : true/false
//     }
//     error : null
// }
//
// POST 주문수락
//
// response
// {
//     success : true
//     response : null
//     error : null
// }
//
// //POST 사진생성요청수락
//
// {
//     success : true
//     response : null
//     error : null
// }
// //GET 주문조회
//
// response
// {
//     success : true
//     response : {
//         id : 1
//         prompt : "이쁘게요"
//         facePictureList : [
//             {
//                 url : "cloudfront"
//             },
//             {
//                 url : "cloudfront"
//             },
//             {
//                 url : "cloudfront"
//             }
//         ]
//         posePictureUrl : "cloudfront"
//         createdAt : timstamp(년월일시분초),
//         modifiedAt : timstamp(년월일시분초)
//     }
//     error : null
// }
//
// //GET 진행중인 작업 조회
//
// response
// {
//     success : true
//     response : {
//         taskList : [
//             {
//                 id : 1,
//                 prompt : "이쁘게요",
//                 facePictureList : [
//                     {
//                         url : "cloudfront"
//                     },
//                     {
//                         url : "cloudfront"
//                     },
//                     {
//                         url : "cloudfront"
//                     }
//                 ],
//                 posePictureUrl : "cloudfront",
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             },
//             {
//                 id : 1,
//                 prompt : "이쁘게요",
//                 facePictureList : [
//                     {
//                         url : "cloudfront"
//                     },
//                     {
//                         url : "cloudfront"
//                     },
//                     {
//                         url : "cloudfront"
//                     }
//                 ],
//                 posePictureUrl : "cloudfront",
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             },
//             {
//                 id : 3,
//                 prompt : "이쁘게요",
//                 facePictureList : [
//                     {
//                         url : "cloudfront"
//                     },
//                     {
//                         url : "cloudfront"
//                     },
//                     {
//                         url : "cloudfront"
//                     }
//                 ],
//                 posePictureUrl : "cloudfront",
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             }
//         ]
//     }
//     error : null
// }
//
// // GET 정산 내역 조회
//
// response
// {
//     success : true
//     response : {
//         balance : 123,
//         settlementList : [
//             // 작업 정산
//             {
//                 id : 1,
//                 submittedPictureUrl : "cloudfront",
//                 date : datetime,
//                 type : "deposit",
//                 amount : 3000,
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             },
//             // 캐시아웃
//             {
//                 id : 2,
//                 submittedPictureUrl : null,
//                 date : datetime,
//                 type : "withdrawal",
//                 amount : -60000,
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             }
//         ]
//     }
//     error : null
// }
//
// // GET 내 등록 계좌 조회
// response
// {
//     success : true
//     response : {
//         name : "안재욱",
//         bankName : "국민은행",
//         accountNumber : "01-010101-010101"
//     },
//     error : null
// }
//
// // GET 완료된 작업 리스트 조회
//
//
// response
// {
//     success : true
//     response : {
//         totalCompletedWorkCount : 25,
//         ratingAverage : 3.1,
//         reviewList : [
//             {
//                 id : 1,
//                 submittedPictureUrl : "cloudfront",
//                 rating : 2.5,
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             },
//             {
//                 id : 2,
//                 submittedPictureUrl : "cloudfront",
//                 rating : 3.5,
//                 createdAt : timstamp(년월일시분초),
//                 modifiedAt : timstamp(년월일시분초)
//             },
//         ]
//     }
//     error : null
// }
//
// response
// {
//     success : true
//     response : {
//         url : "notion 등 링크"
//     }
//     error : null
// }
//
// // GET 사용자 - 내 정보 조회
//
// response
// {
//     success : true,
//     response : {
//         name : "멋쟁이 우기",
//         url : "cloudfront"
//     },
//     error : null
// }