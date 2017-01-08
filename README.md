# saas-api
Project with SaaS API (REST) for Dubai Hackathon

To start server from Maven: `mvn spring-boot:run -Dspring.profiles.active=development`

After this the SaaS web will be availiable at <http://localhost:8080>
 
## REST API description

### Give consent

This service registers new consent of the user.

Request:
`curl -H "Content-Type: application/json" -X POST -d '{"personalDataSubject":"0x95a074c547bc45ecbe98280cac66fd67ea4da743","dataController":"0x7cd1b13da3dd0415c353b97821877577777736b6","processingPurpose":"Processing of the personal data usage consents through myConsents mobile app.","processingPurposeType":"serviceSignUp","processingType":"prerequisite","potentialDisclosures":"none","personalDataAttributes":[{"personalDataType":"name","personalData":"John Doe"},{"personalDataType":"mobileNumber","personalData":"+48207123456"}]}' http://52.166.246.204:8080/consents/give --user 'positiverse:test!@#'`

Response:
`{"giveConsentTxHash":"0xbcb7efb25a2c241eef95f53bfed0cfe77c63f859f6f8b52bc246d2dc9de59e58","personalDataPadding":"0tb2Ow7iFLYDcv5KV1QnFu9ulqTW/hUltD74ugDGIwk="}`

### Withdraw consent

This service registers withdrawal of previously given consent

Request:
`curl -H "Content-Type: application/json" -X POST -d '{"personalDataSubject":"0x95a074c547bc45ecbe98280cac66fd67ea4da743","giveConsentTxHash":"0xbcb7efb25a2c241eef95f53bfed0cfe77c63f859f6f8b52bc246d2dc9de59e58"}' http://52.166.246.204:8080/consents/withdraw --user 'positiverse:test!@#'`

Response:
`{"withdrawConsentTxHash":"0xef293386de9d564473975f370bae07d1231340737c9176c41628f6da16c4136c"}`

### List consents

This service lists all consents given by a user. If a consent is withdrawn, it has non-null withdrawTimestamp field.

Request:
`curl http://52.166.246.204:8080/consents/0x95a074c547bc45ecbe98280cac66fd67ea4da743 --user 'positiverse:test!@#'`

Response:
`[{"giveConsentTxHash":"0xbcb7efb25a2c241eef95f53bfed0cfe77c63f859f6f8b52bc246d2dc9de59e58","personalDataSubject":"0x95a074c547bc45ecbe98280cac66fd67ea4da743","dataController":"0x7cd1b13da3dd0415c353b97821877577777736b6","processingPurpose":"Processing of the personal data usage consents through myConsents mobile app.","processingPurposeType":"serviceSignUp","processingType":"prerequisite","potentialDisclosures":"none","personalDataTypes":["name","mobileNumber"],"personalDataHash":"fObLNSfMuHxVNQQ2ET6aOqMjO7HOnJkb3KYU3cd8Oks=","giveTimestamp":1483789972000,"withdrawTimestamp":1483790133000}]`