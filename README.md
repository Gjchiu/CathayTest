# 國泰世華 JAVA Engineer 線上作業

## 環境設置

- Java: 17
- 資料庫: H2 (Spring Data JPA)
- H2 資料庫控制台: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## 功能簡述

1. 呼叫 Coindesk API，解析其下行內容與資料轉換，並實作新的 API。
    - Coindesk API: [https://api.coindesk.com/v1/bpi/currentprice.json](https://api.coindesk.com/v1/bpi/currentprice.json)

2. 建立一張幣別與其對應中文名稱的資料表（需附建立 SQL 語法），並提供查詢 / 新增 / 修改 / 刪除 功能 API。

3. 查詢幣別請依照幣別代碼排序。

## 實作內容

1. 幣別 DB 維護功能。

2. 呼叫 Coindesk 的 API。

3. 呼叫 Coindesk 的 API，並進行資料轉換，組成新 API。此新 API 提供：
    - 甲、更新時間（時間格式範例：1990/01/01 00:00:00）。
    - 乙、幣別相關資訊（幣別，幣別中文名稱，以及匯率）。

4. 所有功能均須包含單元測試。

5. 排程同步匯率。

## 實作加分題

1. AOP 應用
    - 甲、印出所有 API 被呼叫以及呼叫外部 API 的 request 和 response body log。
    - 乙、Error handling 處理 API response。

2. Swagger UI
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

3. 能夠運行在 Docker

4. 多語系設計