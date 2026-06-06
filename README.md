## Запуск тестов

```bash
mvn clean test
```

## Allure-отчёты

После прогона тесты сохраняют результаты в `target/allure-results`.

Открыть интерактивный отчёт (Maven-плагин):

```bash
mvn allure:serve
```

Сгенерировать статический HTML-отчёт:

```bash
mvn allure:report
```

Отчёт будет в `target/site/allure-maven-plugin`.

Альтернатива — Allure CLI (если установлен глобально):

```bash
allure serve target/allure-results
```
