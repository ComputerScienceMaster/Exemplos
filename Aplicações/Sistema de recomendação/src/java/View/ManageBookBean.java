package View;

import Controllers.BookJpaController;
import Controllers.UserAccountJpaController;
import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import DbPediaModule.DbPediaSearchBooks;
import Model.Book;
import Model.UserAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ManageBookBean")
@SessionScoped
public class ManageBookBean {

    public ManageBookBean() {
    }
    private Book actualBook = new Book();
    private List<Book> listOfBooks = new ArrayList<>();
    private List<Book> listOfBooksDbPedia = new ArrayList<>();
    private BookJpaController controlBook = new BookJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private UserAccountJpaController controlUser = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    public String gotoAddBook() {
        actualBook = new Book();
        return "/Public/BookModule/AddBook.xhtml?faces-redirect=true";
    }
    
    public String gotoSearchOnDbPedia() {
        return "/Public/BookModule/DbPediaSearch.xhtml?faces-redirect=true";
    }
    
    public String gotoEditBook() {
        return "/Public/BookModule/EditBook.xhtml?faces-redirect=true";
    }

    public String gotoManageBooks() {
        loadAllBooks();
        UserAccount usr = ManageSessions.loadUserInSession();
        if(usr != null){
            return "/Public/BookModule/ManageBooks.xhtml?faces-redirect=true";
        }else{
            return "/ErrorLoginFirst.xhtml?faces-redirect=true";
        }
    }

    public String gotoBookDetails(){
        return "/Public/BookModule/DetailsBook.xhtml?faces-redirect=true";
    }
    //CRUD
    public String saveBook() {
        controlBook.create(actualBook);
        actualBook = new Book();
        return gotoManageBooks();
    }

    public String editBook() {
        try {
            controlBook.edit(actualBook);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageBookBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManageBookBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualBook = new Book();
        return "/Public/BookModule/ManageBooks.xhtml?faces-redirect=true";
    }

    public void deleteBook() {
        try {
            controlBook.destroy(actualBook.getIdBook());
            loadAllBooks();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ManageBookBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageBookBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void loadAllBooks() {
        listOfBooks = controlBook.findBookEntities();
    }
    
    public void loadBooksFromDbPedia(){
        listOfBooksDbPedia = DbPediaSearchBooks.searchBooksDbPedia();
    }

    public Book getActualBook() {
        return actualBook;
    }

    public void setActualBook(Book actualBook) {
        this.actualBook = actualBook;
    }

    public BookJpaController getControlBook() {
        return controlBook;
    }

    public void setControlBook(BookJpaController controlBook) {
        this.controlBook = controlBook;
    }

    public List<Book> getListOfBooks() {
        return listOfBooks;
    }

    public void setListOfBooks(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }

    public List<Book> getListOfBooksDbPedia() {
        return listOfBooksDbPedia;
    }

    public void setListOfBooksDbPedia(List<Book> listOfBooksDbPedia) {
        this.listOfBooksDbPedia = listOfBooksDbPedia;
    }

}
