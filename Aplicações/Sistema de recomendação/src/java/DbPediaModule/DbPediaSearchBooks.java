
package DbPediaModule;

import Model.Book;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public class DbPediaSearchBooks {
    
    public static List<Book> searchBooksDbPedia(){
        
        List<Book> booksList = new ArrayList<>();
        
    String sparqlQueryString1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX dbpedia: <http://dbpedia.org/resource/>\n"
                + "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "\n"
                + "select distinct ?title ?author ?country ?genre ?language\n"
                + "where {\n"
                + "  ?s rdf:type dbo:Book;\n"
                + "  dbp:title ?title;\n"
                + "  dbp:author ?author;\n"
                + "  dbp:country ?country;\n"
                + "  dbp:genre ?genre;\n"
                + "  dbp:language ?language.\n"
                + "  \n"
                + "} LIMIT 30";
        //System.out.println(sparqlQueryString1);

        //System.out.println(sparqlQueryString1);
        Query query = QueryFactory.create(sparqlQueryString1);

        // Remote execution.
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {
            // Set the DBpedia specific timeout.
            ((QueryEngineHTTP) qexec).addParam("timeout", "100000");

            // Execute.
            ResultSet rs = qexec.execSelect();
            //Model mode = qexec.execDescribe();
            //ResultSetFormatter.out(System.out, rs);
            //System.out.println(ResultSetFormatter.asText(rs));
            //System.out.println(mode.toString());

            while (rs.hasNext()) {
                QuerySolution sln = rs.nextSolution();
                Literal title = null;
                Literal author = null;
                Literal country = null;
                Literal genre = null;
                Literal language = null;
                
                try {
                    title = sln.getLiteral("title");
                    author = sln.getLiteral("author");
                    country = sln.getLiteral("country");
                    genre = sln.getLiteral("genre");
                    language = sln.getLiteral("language");
                    
                } catch (Exception e) {
                }
                
                Book toAdd = new Book();
                if (title != null) {
                    toAdd.setNameBook(title.getString());
                }
                if (author != null) {
                    toAdd.setAuthorBook(author.getString());
                }
                if (country != null) {
                    toAdd.setCountryBook(country.getString());
                }
                if (genre != null) {
                    toAdd.setGenreBook(genre.getString());
                }
                if (language != null) {
                    toAdd.setLanguageBook(language.getString());
                }
                booksList.add(toAdd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return booksList;
    }
}
