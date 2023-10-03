# 3주차 Update, Delete 흐름 파악



```java
@PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        ...
        return "redirect:/articles";
    }
```

**` return "redirect:/articles";`**

- 클라이언트에게 /articles로 요청을 다시 보내라고 지시.
- 클라이언트는 "redirect:/articles를 받고 다시 /articles로 이동함.
- 이 때 GetMapping발동해서 articles로 이동하게 됨.
- **log.info로 확인해보자!!**

```java
 @GetMapping("/articles")
    public String index(Model model) {
       ...
        log.info("아.. redirect는 얼마나 좋았을까?");
        return "articles/index";
    }
```
- 확인 완료

그냥 실행해보자
/articles/new 로 생성후 다시 목록들어가는 것도 귀찮다.
그리고 매번 생성하는 것도 귀찮다!
```sql
INSERT INTO article(id, title, content) VALUES(1, '가가가가', '1111');
INSERT INTO article(id, title, content) VALUES(2, '나나나나', '2222');
INSERT INTO article(id, title, content) VALUES(3, '다다다다', '3333');
```
`resources/data.sql` 에 위 코드 붙여넣기

```properties
#application.properties
spring.jpa.deferdatasource-initialization=true
```
`application.properites` 에 위 코드 붙여넣기

이제부터 data를 일일히 써놓지 않아도 시작부터 쿼리문을 날려줌
이제 기초 세팅 되었으니 수정을 실시해보자.

<a href="articles/{{article.id}}/edit">Edit</a>

