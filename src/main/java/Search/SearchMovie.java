package Search;

import Module.Movie;

import java.util.List;

public class  SearchMovie {
    private int page;
    private List<Movie.movie_detail> results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie.movie_detail> getResults() {
        return results;
    }

    public void setResults(List<Movie.movie_detail> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
