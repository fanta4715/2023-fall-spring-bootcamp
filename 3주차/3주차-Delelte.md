# 3주차 Delete 흐름 파악

show 머스테치에 추가
<a href="/articles/{{article.id}}/delelte">Delete</a>

@GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id){
        Article target=articleRepository.findById(id).orElse(null);
        articleRepository.delete(target);
        return "redirect:/articles";

    }

삭제 끗!
