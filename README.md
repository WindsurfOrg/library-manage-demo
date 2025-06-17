# 圖書館管理系統 (Library Management System)

`cbe-svc-library-manage` 是一個基於 Java Spring Boot 的後端服務，提供圖書管理、借閱、歸還等核心功能。

## 主要功能

- **書籍管理**: 新增、修改、刪除和查詢書籍資訊。
- **借閱管理**: 處理書籍的借閱和歸還流程。
- **逾期追蹤**: 查詢逾期未還的書籍。
- **API 文件**: 透過 Swagger 自動產生 API 文件，方便前端或其他服務整合。

## 技術棧

- **核心框架**: Spring Boot 2.2.6.RELEASE
- **程式語言**: Java 11
- **Web 框架**: Spring Web MVC (用於建構 RESTful API)
- **資料存取**: Spring Data JPA
- **資料庫**: H2 Database (嵌入式記憶體資料庫，主要用於開發與測試)
- **API 文件**: Springfox Swagger 3.0.0
- **工具庫**:
    - Lombok: 簡化樣板程式碼
    - Jackson: JSON 資料處理
- **建置工具**: Apache Maven
- **日誌框架**: Logback

## 如何開始

### 先決條件

- Java Development Kit (JDK) 11 或更高版本
- Apache Maven 3.6.x 或更高版本

### 安裝與執行

1.  **複製專案**:
    ```bash
    git clone <repository-url>
    cd library-manage
    ```

2.  **建置專案**:
    使用 Maven 進行建置。
    ```bash
    mvn clean install
    ```

3.  **執行應用程式**:
    應用程式將使用 `application-local.properties` 中的設定，啟動一個嵌入式的 H2 資料庫。
    ```bash
    mvn spring-boot:run
    ```
    或者，您可以執行建置好的 JAR 檔案:
    ```bash
    java -jar target/cbe-svc-library-manage-1.0.0.jar
    ```

4.  **訪問應用程式**:
    - API 端點基礎路徑: `http://localhost:8080` (預設埠號，如果 `server.port` 未在 `application.properties` 中設定)
    - Swagger API 文件: `http://localhost:8080/swagger-ui/`
    - H2 資料庫控制台: `http://localhost:8080/h2-console` (請確保 `spring.h2.console.enabled=true` 且 JDBC URL 為 `jdbc:h2:mem:testdb`)

## API 端點概覽

所有 API 端點皆為 `POST` 請求，基礎路徑為 `/book`。

- `POST /book/add`: 新增書籍。
- `POST /book/modify`: 修改書籍資訊。
- `POST /book/delete`: 刪除書籍。
- `POST /book/query`: 查詢書籍。
- `POST /book/borrow`: 借閱書籍。
- `POST /book/return`: 歸還書籍。
- `POST /book/overdue`: 查詢逾期書籍。

詳細的請求與回應格式請參考 Swagger API 文件。

## 系統架構概覽

本系統採用典型的分層架構，主要包含以下層次：

- **API 層 (Controller)**: 接收 HTTP 請求，調用服務層。
- **服務層 (Service)**: 處理核心業務邏輯。
- **資料存取層 (Repository)**: 與資料庫互動。
- **實體層 (Entity)**: 資料庫表的物件映射。
- **DTOs**: 在各層之間傳輸資料。

詳細的系統架構圖和各模組說明，請參閱 [SYSTEM_ARCHITECTURE.md](SYSTEM_ARCHITECTURE.md)。

## 設定

- **主要設定**: `src/main/resources/application.properties`
    - `library.borrow.day=30`: 書籍預設借閱天數。
- **本地開發設定**: `src/main/resources/application-local.properties`
    - H2 資料庫連接資訊。
    - 啟用 H2 控制台。

## 貢獻

歡迎提交問題報告 (Issues) 或合併請求 (Pull Requests)。
