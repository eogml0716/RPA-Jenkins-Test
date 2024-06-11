//package org.example;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.Arrays;
//
//public class SSVParser {
//
//    public static void main(String[] args) {
//        String ssvData = "SSV:UTF-8\u001EErrorCode:int=0\u001EDataset:dsMaster\u001E_RowType_\u001FcentCd:string(32)\u001FcentNm:string(32)\u001FconvQty:string(32)\u001FcpmAmt:string(32)\u001FcpmSumAmt:int(4)\u001FcustCd:string(32)\u001FdftxAmt:int(4)\u001FdlvySchd:string(32)\u001FdlvySchdYmd:string(32)\u001FediCrtDt:string(32)\u001FediTrnmDvCd:string(32)\u001FitemBarc:string(32)\u001FitemCd:string(32)\u001FitemCpctVal:string(32)\u001FitemLrdvCd:string(32)\u001FitemNm:string(32)\u001FitemUnitQty:string(32)\u001FntCn:string(32)\u001FordClosYn:string(32)\u001FordConvQty:int(4)\u001FordCpm:string(32)\u001FordDetmDvCd:string(32)\u001FordDvCd:string(32)\u001FordMainagDvCd:string(32)\u001FordQty:string(32)\u001FordShapCd:string(32)\u001FordSlipNo:string(32)\u001FordUnitDvCd:string(32)\u001FordYmd:string(32)\u001FregDt:string(32)\u001FregUserId:string(32)\u001FshpFnoCd:string(32)\u001FslemSumAmt:int(4)\u001FtxAmt:int(4)\u001FupdDt:string(32)\u001FupdUserId:string(32)\u001FvatAmt:int(4)\u001FvndCd:string(32)\u001FwarhConvQty:int(4)\u001FwarhYmd:string(32)\u001FwmsCrtDt:string(32)\u001EN\u001F\u0003\u001F\u0003\u001F11\u001F23100\u001F\u0003\u001F\u0003\u001F20240516\u001F\u0003\u001F\u0003\u001F8809243174835\u001F073509\u001F273g\u001F\u0003\u001F대광)찰진순대273g\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F2100\u001F\u0003\u001F0\u001F\u0003\u001F11\u001F0\u001F\u0003\u001F낱개\u001F20240513\u001F\u0003\u001F\u0003\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001E" +
//                "N\u001F\u0003\u001F\u0003\u001F571\u001F1199100\u001F\u0003\u001F\u0003\u001F20240515\u001F\u0003\u001F\u0003\u001F8809243174835\u001F073509\u001F273g\u001F\u0003\u001F대광)찰진순대273g\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F2100\u001F\u0003\u001F0\u001F\u0003\u001F571\u001F0\u001F\u0003\u001F낱개\u001F20240513\u001F\u0003\u001F\u0003\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001E" +
//                "N\u001F\u0003\u001F\u0003\u001F9\u001F18900\u001F\u0003\u001F\u0003\u001F20240517\u001F\u0003\u001F\u0003\u001F8809243174835\u001F073509\u001F273g\u001F\u0003\u001F대광)찰진순대273g\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F2100\u001F\u0003\u001F0\u001F\u0003\u001F9\u001F0\u001F\u0003\u001F낱개\u001F20240514\u001F\u0003\u001F\u0003\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001E" +
//                "N\u001F\u0003\u001F\u0003\u001F341\u001F716100\u001F\u0003\u001F\u0003\u001F20240516\u001F\u0003\u001F\u0003\u001F8809243174835\u001F073509\u001F273g\u001F\u0003\u001F대광)찰진순대273g\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F2100\u001F\u0003\u001F0\u001F\u0003\u001F341\u001F0\u001F\u0003\u001F낱개\u001F20240514\u001F\u0003\u001F\u0003\u001F1\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001E";
//
//        JSONObject jsonResult = parseSSVToJson(ssvData);
//        System.out.println(jsonResult.toString(2));
//    }
//
//    public static JSONObject parseSSVToJson(String ssvData) {
//        JSONObject resultJson = new JSONObject();
//        JSONArray dataArray = new JSONArray();
//
//        String[] lines = ssvData.split("\u001E");
//        String[] headers = null;
//        boolean isDataSection = false;
//
//        // 정확한 헤더를 추출하기 위해 SSV 데이터에서 _RowType_ 다음의 라인을 처리합니다.
//        String[] headersInKorean = {"주문일자", "납품요청일", "편수", "발주형태", "발주구분", "상품코드", "판매코드", "상품명", "발주단위", "규격", "원가", "입수", "주문수", "주문수량", "주문금액"};
//
//        for (String line : lines) {
//            System.out.println("Processing line: " + line);
//            if (line.startsWith("Dataset")) {
//                isDataSection = true;
//            } else if (isDataSection && line.startsWith("_RowType_")) {
//                headers = headersInKorean;  // 헤더를 한글로 설정
//                System.out.println("Parsed headers: " + Arrays.toString(headers));
//            } else if (isDataSection && !line.startsWith("SSV:") && !line.startsWith("ErrorCode:")) {
//                String[] values = line.split("\u001F");
//                System.out.println("Parsed values: " + Arrays.toString(values));
//
//                // Remove unnecessary elements from values
//                values = Arrays.stream(values)
//                        .filter(value -> !value.equals("\u0003") && !value.trim().isEmpty())
//                        .toArray(String[]::new);
//                System.out.println("Filtered values: " + Arrays.toString(values));
//
//                // Ensure headers and values length match
//                if (headers != null && headers.length == values.length - 1) {  // Adjusted for extra 'N'
//                    JSONObject dataObject = new JSONObject();
//                    for (int i = 0; i < headers.length; i++) {
//                        dataObject.put(headers[i], values[i + 1]);  // Skip the first 'N' value
//                    }
//                    dataArray.put(dataObject);
//                } else {
//                    System.out.println("Header and values length mismatch: " + (headers != null ? headers.length : "null") + " vs " + (values.length - 1));
//                }
//            }
//        }
//
//        resultJson.put("data", dataArray);
//        return resultJson;
//    }
//}
