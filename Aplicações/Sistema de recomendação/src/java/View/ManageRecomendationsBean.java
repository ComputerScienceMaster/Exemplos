package View;

import Controllers.BookJpaController;
import Controllers.RatingsJpaController;
import Controllers.UserAccountJpaController;
import Controllers.exceptions.NonexistentEntityException;
import Model.Book;
import Model.Ratings;
import Model.RatingsPK;
import Model.UserAccount;
import PredicionModule.PredictUsingApacheMahout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.mahout.cf.taste.common.TasteException;
import org.primefaces.event.RateEvent;

@ManagedBean(name = "ManageRecomendationBean")
@SessionScoped
public class ManageRecomendationsBean {

    private Book actualBook = new Book();
    private Integer rate;
    private List<Book> listOfBooks = new ArrayList<>();
    private List<Ratings> listOfRatings = new ArrayList<>();
    private List<Book> listOfSuggestions = new ArrayList<>();
    private BookJpaController controlBook = new BookJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private UserAccountJpaController controlUser = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private RatingsJpaController controlRatings = new RatingsJpaController(EmProvider.getInstance().getEntityManagerFactory());

//gotos
    public String gotoClassify() {
        UserAccount usr = ManageSessions.loadUserInSession();
        if (usr != null) {
            return "/Public/RecomendationModule/Classify.xhtml?faces-redirect=true";
        } else {
            return "/ErrorLoginFirst.xhtml?faces-redirect=true";
        }
    }

    public String gotoSuggestMe() {
        if (ManageSessions.getUserId() != null) {
            return "/Public/RecomendationModule/SuggestMe.xhtml?faces-redirect=true";
        } else {
            return "/ErrorLoginFirst.xhtml?faces-redirect=true";
        }

    }

    public void onrate(RateEvent rateEvent) {
        Ratings r = new Ratings();
        RatingsPK rpk = new RatingsPK();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int bookId = 0;

        String actionType = params.get("actionType");

        if ("CreateBookAndRate".equals(actionType)) {
            Book toCreate = new Book();
            toCreate.setNameBook(params.get("name"));
            toCreate.setAuthorBook(params.get("author"));
            toCreate.setLanguageBook(params.get("language"));
            if (params.get("pages") != null) {
                toCreate.setPagesBook(Integer.parseInt(params.get("pages")));
            }
            toCreate.setPublisherBook(params.get("publisher"));
            toCreate.setGenreBook(params.get("genre"));
            bookId = controlBook.create(toCreate);
        }

        //get the book 
        if ("newRating".equals(actionType) || "editRating".equals(actionType)) {
            bookId = Integer.parseInt(params.get("bookId"));
        }
        Integer stars = 0;
        try {
            stars = (Integer) rateEvent.getRating();
        } catch (Exception e) {
            stars = Integer.parseInt(String.valueOf(rateEvent.getRating()));
        }
        
        actualBook = controlBook.findBook(bookId);
        //get the user
        UserAccount user = ManageSessions.loadUserInSession();

        //get the RatingsPK
        rpk.setIdBook(bookId);
        rpk.setIdUserAccount(user.getIdUserAccount());

        //Ser book, user, stars, RatingsPK
        r.setStars(stars);
        r.setBook(actualBook);
        r.setUseraccount(user);
        r.setRatingsPK(rpk);
        //persist
        try {
            if ("newRating".equals(actionType) || "CreateBookAndRate".equals(actionType)) {
                controlRatings.create(r);
            } else if ("editRating".equals(actionType)) {
                controlRatings.edit(r);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManageRecomendationsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void oncancel() {
        Ratings r = new Ratings();
        RatingsPK rpk = new RatingsPK();
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int bookId = Integer.parseInt(params.get("bookId"));
        Book toDelete = new Book();
        
        rpk.setIdBook(bookId);
        rpk.setIdUserAccount(ManageSessions.getUserId());

        //Ser book, user, stars, RatingsPK
        r.setBook(toDelete);
        r.setUseraccount(ManageSessions.loadUserInSession());
        r.setRatingsPK(rpk);
        
        try {
            controlRatings.destroy(rpk);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageRecomendationsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadSuggestions(int numberOfSuggestions) {
        List<Ratings> result = controlRatings.findRatingsEntities();
        try {
            listOfSuggestions = PredictUsingApacheMahout.suggestMeBook(result, numberOfSuggestions);
        } catch (IOException ex) {
            Logger.getLogger(ManageRecomendationsBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TasteException ex) {
            Logger.getLogger(ManageRecomendationsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUserBooks() {
        UserAccount ac = ManageSessions.loadUserInSession();
        if (ac != null) {
            listOfRatings = ac.getRatingsList();
        }
    }

    public void loadBooks() {
        listOfBooks = controlBook.findBookEntities();
    }

    public void loadBooksNotRatedByUser() {
        listOfBooks = controlBook.findBookEntities();
        UserAccount ac = ManageSessions.loadUserInSession();
        List<Ratings> ratings = ac.getRatingsList();
        for (Ratings r : ratings) {
            listOfBooks.remove(r.getBook());
        }
    }

    public List<Book> getListOfBooks() {
        return listOfBooks;
    }

    public void setListOfBooks(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }

    public Book getActualBook() {
        return actualBook;
    }

    public void setActualBook(Book actualBook) {
        this.actualBook = actualBook;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<Ratings> getListOfRatings() {
        return listOfRatings;
    }

    public void setListOfRatings(List<Ratings> listOfRatings) {
        this.listOfRatings = listOfRatings;
    }

    public List<Book> getListOfSuggestions() {
        return listOfSuggestions;
    }

    public void setListOfSuggestions(List<Book> listOfSuggestions) {
        this.listOfSuggestions = listOfSuggestions;
    }

}
