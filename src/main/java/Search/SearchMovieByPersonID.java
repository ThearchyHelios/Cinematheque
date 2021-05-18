package Search;

import Module.Movie;

import java.util.List;

/**
 * @project: Cinematheque
 * @author: Yilun JIANG
 * @date: 18/05/2021 02:05
 */
public class SearchMovieByPersonID {
    private List<Movie.movie_detail> cast;

    public SearchMovieByPersonID(List<Movie.movie_detail> cast) {
        this.cast = cast;
    }

    public List<Movie.movie_detail> getCast() {
        return cast;
    }

    public void setCast(List<Movie.movie_detail> cast) {
        this.cast = cast;
    }
}
