# 3주차 Update 흐름 파악



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
BootCampController 생성

```mustache
{{>layouts/header}}

{{#article}}
    <form class="container" action="/articles/update" method="post">
        <input name="id" type="hidden" value="{{id}}">
        <div class="mb-3">
            <label class="form-label">제목</label>
            <input type="text" class="form-control" name="title" value="{{title}}">
        </div>
        <div class="mb-3">
            <label class="form-label">내용</label>
            <textarea class="form-control" rows="3" name="content">{{content}}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <a href="/articles/{{id}}">Back</a>
    </form>
{{/article}}

{{>layouts/footer}}
```
edit anchor통해서 "/articles/{{article.id}}/edit"으로 이동하면,
controller가 받아서 해당 정보를 edit에 넘기는 형식

```java
    @Autowired
    ArticleRepository articleRepository;
    //<a href="articles/{{article.id}}/edit">Edit</a> 를 통해서 여기서 GetMapping에 잡힘
    //이렇게 두 번을 거쳐서 edit.mustache로 보내는 이유 : model객체를 사용하기 위함
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        //수정하기 전 데이터를 가져오기 위해서, id를 통해서 target을 가져옴
        Article target=articleRepository.findById(id).orElse(null);

        //target의 내용을 model객체에 추가
        model.addAttribute("article",target);

        //view 반환
        return "articles/edit";
    }
```

dto 새로 생성 ArticleUpdateForm
    @PostMapping("/articles/update")
    public String update(ArticleUpdateForm dto){
        Article article = dto.toEntity()
        articleRepository.save(target);
        return "redirect:/articles/"+target.getId();
    }

위 코드의 문제점
개선
    @PostMapping("/articles/update")
    public String update(ArticleUpdateForm dto){
        long id = dto.getId();
        Article target = articleRepository.findById(id).orElse(null);
        target.update(dto);
        articleRepository.save(target);
        return "redirect:/articles/"+target.getId();
    }
수정 끝!
