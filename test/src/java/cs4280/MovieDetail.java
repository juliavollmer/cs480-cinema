
package cs4280;

/**
 *
 * @author jsvollmer2
 */
public class MovieDetail {

    private String mid = "Missing item number";
    private String name, intro, genre, runtime, category, language, director, cast, cover, release;

    public MovieDetail(String mid, String name, String intro,
            String genre, String runtime, String category,
            String language, String director, String cast, String cover, String release) {
        setMid(mid);
        setName(name);
        setIntro(intro);
        setGenre(genre);
        setRuntime(runtime);
        setCategory(category);
        setLanguage(language);
        setDirector(director);
        setCast(cast);
        setCover(cover);
        setRelease(release);
    }

    public MovieDetail() {
    }

    public String getMid() {
        return (mid);
    }

    private void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return (name);
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return (intro);
    }

    private void setIntro(String intro) {
        this.intro = intro;
    }

    public String getGenre() {
        return (genre);
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return (runtime);
    }

    private void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getCategory() {
        return (category);
    }

    private void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return (language);
    }

    private void setLanguage(String language) {
        this.language = language;
    }

    public String getDirector() {
        return (director);
    }

    private void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return (cast);
    }

    private void setCast(String cast) {
        this.cast = cast;
    }

    public String getCover() {
        return (cover);
    }

    private void setCover(String cover) {
        this.cover = cover;
    }

    public String getRelease() {
        return (release);
    }

    private void setRelease(String release) {
        this.release = release;
    }
}
