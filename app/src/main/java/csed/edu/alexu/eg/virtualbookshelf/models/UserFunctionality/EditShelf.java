package csed.edu.alexu.eg.virtualbookshelf.models.UserFunctionality;

import android.util.Log;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Bookshelf;

import java.io.IOException;
import java.util.List;

public class EditShelf {
    private final String TAG = EditShelf.class.getSimpleName();

    // Todo change method name and refactor the code.
    public void AddVolumeToShelf(String shelfID, String volumeID, Books  books) {
        if (shelfID == null || volumeID == null)
            throw new RuntimeException("Invalid args");
        Books.Mylibrary.Bookshelves.AddVolume bookShelve = null;
        try {
            //params[0]: ShelfID, params[1]: volumeID
            Log.d(TAG, "Before adding to bookshelf");
            bookShelve = books.mylibrary().bookshelves().addVolume(shelfID, volumeID);
            bookShelve.execute();
            Log.d(TAG, "After adding to bookshelf");
        } catch (IOException e) {
            Log.e(TAG, "Error in add book volume");
            throw new RuntimeException("Failed to add book to shelf");
        }
    }

    public void clearShelf (String shelfID, Books books){
        if (shelfID == null)
            throw new RuntimeException("Invalid args");
        try {
            Books.Mylibrary.Bookshelves.ClearVolumes bookShelve = null;
            Log.d(TAG, "Before deleting from bookShelf");
            bookShelve = books.mylibrary().bookshelves().clearVolumes(shelfID);
            bookShelve.execute();
            Log.d(TAG, "After deleting from bookShelf");
        } catch (IOException e) {
            Log.e(TAG, "Error in remove book volume");
            throw new RuntimeException("Failed to clear shelf");
        }

    }
    public void deleteFromShelf (String shelfID, String volumeID, Books books){
        if (shelfID == null || volumeID == null)
            throw new RuntimeException("Invalid args");
        try {
            Books.Mylibrary.Bookshelves.RemoveVolume bookShelve = null;
            Log.d(TAG, "before delete from bookShelf");
            bookShelve = books.mylibrary().bookshelves().removeVolume(shelfID, volumeID);
            bookShelve.execute();
            Log.d(TAG, "after delete from bookShelf");
        } catch (IOException e) {
            Log.e(TAG, "Error in remove book volume");
            throw new RuntimeException("Failed to delete book from shelf");
        }
    }
}
