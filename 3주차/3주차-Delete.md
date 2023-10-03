# 3주차 Delete 흐름 파악
## delete template
<!-- show.mustache에 추가-->
<a href="/articles/{{article.id}}/delelte">Delete</a>

**delete에 template가 필요할까?**

## delete controller
```java
@GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id){
        Article target=articleRepository.findById(id).orElse(null);
        articleRepository.delete(target);
        return "redirect:/articles";

    }
```
삭제 끗!
