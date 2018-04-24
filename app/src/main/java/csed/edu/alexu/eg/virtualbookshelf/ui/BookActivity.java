package csed.edu.alexu.eg.virtualbookshelf.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import csed.edu.alexu.eg.virtualbookshelf.R;
import csed.edu.alexu.eg.virtualbookshelf.utility.BookListAdapter;
import csed.edu.alexu.eg.virtualbookshelf.utility.EditFactory;
import csed.edu.alexu.eg.virtualbookshelf.utility.FilterData;

public class BookActivity extends AppCompatActivity {
    private static final String SEPARATOR = " , ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Log.d(this.getClass().getName(), "open individual book number ");

        String volumeId = (String) this.getIntent().getExtras().get("book_id");

        new BookActivity.LoadSingleBookTask().execute(volumeId);
    }


    /**
     *
     */
    private class LoadSingleBookTask extends AsyncTask<String, Void, Volume> {

        @Override
        protected Volume doInBackground(String... volumeId) {
            Volume volume = null;
            try {
                // TODO : REMOVE THIS
                // get book volume
                Books.Volumes.Get listBooksInst = EditFactory.getInstance().getBooks().volumes().get(volumeId[0]);

                volume = listBooksInst.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return volume;
        }

        @Override
        protected void onPostExecute(Volume volume) {
            super.onPostExecute(volume);
            if (volume != null) {
                // ui items
                ImageView bookImage = findViewById(R.id.ind_book_image);
                TextView bookTitleTxt = findViewById(R.id.ind_book_title);
                TextView authorsTxt = findViewById(R.id.ind_book_authors);

                // image
                Volume.VolumeInfo.ImageLinks imageLinks = volume.getVolumeInfo().getImageLinks();
                if (imageLinks != null) {
                    Log.d("VIEW-BOOK"," Book image link : " + imageLinks.getMedium() );
                    //Picasso.with(BookActivity.this).load(imageLinks.getMedium()).resize(500,500).into(bookImage);
                    Picasso.with(BookActivity.this).load(imageLinks.getMedium()).into(bookImage);
                }

                // title
                bookTitleTxt.setText(volume.getVolumeInfo().getTitle());
                Log.d("VIEW-BOOK"," Book title : " + volume.getVolumeInfo().getTitle() );
                List<String> authors = volume.getVolumeInfo().getAuthors();

                // authors
                if (authors != null && !authors.isEmpty()) {
                    Log.d("VIEW-BOOK",authors.size()+ ": " );
                    StringBuilder authorsJoined = new StringBuilder();
                    for (String author : authors) {
                        Log.d("VIEW-BOOK", " author : "+author );
                        authorsJoined.append(author);
                        authorsJoined.append(SEPARATOR);
                    }
                    String authorsStr = authorsJoined.toString();
                    //Remove last comma
                    authorsStr = authorsStr.substring(0, authorsStr.length() - SEPARATOR.length());
                    Log.d("VIEW-BOOK", " ALL authors : "+ authorsStr );
                    authorsTxt.setText(authorsStr);
                }
            }
        }

    }

}


