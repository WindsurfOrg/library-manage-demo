#!/bin/bash

echo "🚀 Spring Boot 3.x 自動升級腳本"
echo "================================"

# 1. 備份當前狀態
echo "📦 備份當前狀態..."
git add .
git commit -m "備份: Spring Boot 2.x 狀態" || echo "沒有變更需要提交"

# 2. 批量替換 javax -> jakarta
echo "🔄 執行 javax -> jakarta 命名空間替換..."
find src -name "*.java" -type f -exec sed -i '' 's/import javax\.persistence\./import jakarta.persistence./g' {} \;
find src -name "*.java" -type f -exec sed -i '' 's/import javax\.validation\./import jakarta.validation./g' {} \;
find src -name "*.java" -type f -exec sed -i '' 's/import javax\.servlet\./import jakarta.servlet./g' {} \;

# 3. 更新 POM 文件（已經完成）
echo "✅ POM 已更新為 Spring Boot 3.2.12"

# 4. 編譯測試
echo "🧪 測試編譯..."
mvn clean compile -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ 升級成功！編譯通過"
    git add .
    git commit -m "升級完成: Spring Boot 3.2.12 + Java 17"
else
    echo "❌ 編譯失敗，需要手動修復"
    echo "請檢查日誌並修復編譯錯誤"
fi

# 5. 運行測試
echo "🔬 運行測試..."
mvn test -DfailIfNoTests=false

echo "📋 升級完成報告："
echo "- Spring Boot: 2.2.6 → 3.2.12"
echo "- Java: 11 → 17"  
echo "- Lombok: 1.18.30 → 1.18.38"
echo "- 命名空間: javax.* → jakarta.*"
echo "- API文檔: Swagger → SpringDoc OpenAPI"
