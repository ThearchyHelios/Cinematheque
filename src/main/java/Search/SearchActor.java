package Search;

import java.util.List;
import Module.Actor;

/**
 * @project: Cinematheque
 * @author: Yilun JIANG
 * @date: 17/05/2021 21:54
 */
public class SearchActor {
    private int page;
    private List<Actor.actor_detail> results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Actor.actor_detail> getResults() {
        return results;
    }

    public void setResults(List<Actor.actor_detail> results) {
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
