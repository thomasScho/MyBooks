package fr.schollaert.mybooks;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FindBooksActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);
        findViewById(R.id.envoyerRecherche).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int click = v.getId();
        if (click == R.id.envoyerRecherche) {
            String titleToFind = ((EditText) findViewById(R.id.findingText)).getText().toString();
            if (titleToFind.length() > 0) {
                titleToFind = titleToFind.replace(" ", "+");
                String url = "https://www.googleapis.com/books/v1/volumes?q=";
                url = url + titleToFind;

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        List<Book> bookList = new ArrayList<Book>();

                        String json = new String(responseBody);

                        try {
                            JSONObject object = new JSONObject(json);
                            JSONArray array = object.getJSONArray("items");

                            for (int i = 0; i < array.length(); i++) {
                                Book book = new Book();
                                JSONObject item = array.getJSONObject(i);

                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                                String title = volumeInfo.getString("title");
                                book.setTitle(title);

                                JSONArray authors = volumeInfo.getJSONArray("authors");
                                String author = authors.getString(0);
                                book.setAuthor(author);

                                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                String imageLink = imageLinks.getString("smallThumbnail");
                                book.setImageUrl(imageLink);

                                bookList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (bookList.size() == 0) {
                            Toast.makeText(FindBooksActivity.this, "Aucun livre n'a été trouvé avec ce titre", Toast.LENGTH_LONG).show();
                        }
                        LibraryAdapter library = new LibraryAdapter(FindBooksActivity.this, bookList);
                        ListView lv = (ListView) findViewById(R.id.listBookView);
                        lv.setAdapter(library);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }

                });
            }
        }
    }
}