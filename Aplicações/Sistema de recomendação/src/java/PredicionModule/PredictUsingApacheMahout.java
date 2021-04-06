package PredicionModule;

import Controllers.BookJpaController;
import Model.Book;
import Model.Ratings;
import View.EmProvider;
import View.ManageSessions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class PredictUsingApacheMahout {

    private static BookJpaController  controlBook = new BookJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    public static List<Book> suggestMeBook(List<Ratings> ratings, int numberOfRecomendations) throws IOException, TasteException {
        List<Book> books = new ArrayList<>();
        File file = printCSVFile(ratings);
        //File file = new File("C:\\Users\\USER\\Documents\\GitHub\\CSM-Suggest-Me\\src\\java\\PredicionModule\\cursos.csv");
        FileDataModel model = new FileDataModel(file);
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        List<RecommendedItem> recommendations = recommender.recommend(ManageSessions.getUserId(), numberOfRecomendations);
        for (RecommendedItem recommendation : recommendations) {
            Book toAdd = controlBook.findBook((int)recommendation.getItemID());
            books.add(toAdd);
        }
        return books;
    }

    private static File printCSVFile(List<Ratings> ratings) {
        File f = new File("MahoutPredict.csv");
        PrintWriter pw;
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException ex) {
            return f;
        }
        for (Ratings r : ratings) {
            StringBuilder sb = new StringBuilder();
            sb.append(r.getUseraccount().getIdUserAccount());
            sb.append(',');
            sb.append(r.getBook().getIdBook());
            sb.append(',');
            sb.append(r.getStars());
            sb.append('\n');
            pw.write(sb.toString());
        }
        pw.close();
        System.out.println("done!");
        return f;

    }
}
