package eu.komanda30.kupra.indexing;

import eu.komanda30.kupra.entity.Recipe;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

public class AuthorIdFieldBridge implements FieldBridge {
    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
        if (!(value instanceof Recipe)) {
            throw new IllegalArgumentException("Illegal argument, needed type Recipe!");
        }

        final Recipe recipe = (Recipe) value;
        luceneOptions
                .addFieldToDocument(name + ".authorId", recipe.getAuthor().getUserId(), document);
    }
}
