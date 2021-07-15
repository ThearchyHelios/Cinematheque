package Module;

import java.util.List;

/**
 * @project: Cinematheque
 * @author: Yilun JIANG
 * @date: 17/05/2021 21:47
 */
public class Actor extends Movie {
    public static class actor_detail{
        private String profile_path;
        private boolean adult;
        private int id;
        private List<movie_detail> known_for;
        private String name;
        private double popularity;
        private String birthday;
        private String deathday;
        private String biography;
        private String place_of_birth;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getDeathday() {
            return deathday;
        }

        public void setDeathday(String deathday) {
            this.deathday = deathday;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getPlace_of_birth() {
            return place_of_birth;
        }

        public void setPlace_of_birth(String place_of_birth) {
            this.place_of_birth = place_of_birth;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<movie_detail> getKnown_for() {
            return known_for;
        }

        public void setKnown_for(List<movie_detail> known_for) {
            this.known_for = known_for;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }
    }
}
