package cz.tyckouni.poopio.core.dao;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.tyckouni.poopio.base.entities.Poop;
import cz.tyckouni.poopio.base.exceptions.InternalErrorException;

public class FBPoopsDaoImpl implements PoopsDao {

    private static final String TAG = "FBPoopsDaoImpl";

    private DatabaseReference mPoopsReference = FirebaseDatabase.getInstance()
            .getReference().child("poops");

    @Override
    public void create(Poop poop, FirebaseUser user, DatabaseReference.CompletionListener listener) {
        DatabaseReference userPoopsReference = mPoopsReference.child(user.getUid());
        String poopKey = userPoopsReference.push().getKey();

        poop.setUid(poopKey);
        if (poopKey == null) {
            throw new InternalErrorException("Reference was pointing to the Root instead of new poop.");
        }

        userPoopsReference.child(poopKey).setValue(poop, listener);

        Log.d(TAG, "create: '" + poop + "' for user: '" + user.getUid() + "'");
    }

    @Override
    public void delete(Poop poop, FirebaseUser user, DatabaseReference.CompletionListener listener) {
        DatabaseReference poopReference = mPoopsReference.child(user.getUid()).child(poop.getUid());

        poopReference.removeValue(listener);

        Log.d(TAG, "delete: '" + poop + "' for user: '" + user.getUid() +"'");
    }
}
