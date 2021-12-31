package com.licia_josh.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.licia_josh.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class CreateWriteUpActivity extends AppCompatActivity {

    private Menu menu;
    //    Button postButton;
    TextView postButton;
    EditText postWriteUp, postHeading;
    ImageView displayPostImage;
    ImageView closeBtn;
    VideoView displayPostVideo;
    ImageView addVideo, playButton;
    ImageView addImage;
    private static final int GalleryPick = 1;
    private static final int GalleryVideoPick = 1;
    String PostWriteUp, CelebrationPicked, PostHeading;
    private ProgressDialog mProgress;
    private Uri imageUri, videoUri, imageUri2;
    private StorageReference postRefImages, postRefVideo;
    private DatabaseReference postRef, UserRef, RootRef, clickPostRef;

    boolean isImageAdded, isVideoAdded;

    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, current_user_id, PostRandomName;

    private long countPost = 0;
    String uid, postid, PostKey, description, PostImage, databaseUserID;

    private Toolbar toolbar;
    private DatabaseReference CelebrationRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_write_up);

        closeBtn = findViewById(R.id.btn_close_account_setting);

        closeBtn.setOnClickListener(v -> {
            finish();
        });

        postButton = findViewById(R.id.postPost);
        postButton.setOnClickListener(v -> {
            SavePosts();
        });

//        postButton = findViewById(R.id.buttonPost);
        postWriteUp = findViewById(R.id.edt_postWriteUp);
        postHeading = findViewById(R.id.edt_postHeading);
        displayPostImage = findViewById(R.id.imageView);

//        displayPostVideo = findViewById(R.id.videoView);
        addImage = findViewById(R.id.pickImage);
//        addVideo= findViewById(R.id.pickVideo);


     /*   arrayList_Celebration = new ArrayList<>();
        arrayList_Celebration.add("Select Your Celebration");
        arrayList_Celebration.add("Birthday");
        arrayList_Celebration.add("Graduation");
        arrayList_Celebration.add("Wedding");
        arrayList_Celebration.add("Marriage ceremony");
        arrayList_Celebration.add("Valentine");
        arrayList_Celebration.add("Proposal");
        arrayList_Celebration.add("Outing");
        arrayList_Celebration.add("Good time");
        arrayList_Celebration.add("Festival");
        arrayList_Celebration.add("Happy moment");
        arrayList_Celebration.add("Anniversary");
        arrayList_Celebration.add("Success");
        arrayList_Celebration.add("Life");
        arrayList_Celebration.add("Grace");*/

        CelebrationRef = FirebaseDatabase.getInstance().getReference().child("Celebrations");

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        RootRef = FirebaseDatabase.getInstance().getReference();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRefImages = FirebaseStorage.getInstance().getReference().child("PostImages");
        postRefVideo = FirebaseStorage.getInstance().getReference().child("PostVideos");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");
//        PostKey = getIntent().getExtras().get("PostKey").toString();
//        clickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey);

        mProgress = new ProgressDialog(this);

        addImage.setOnClickListener(v -> {
            CropImage.activity().
                    setAspectRatio(4, 3)
                    .start(this);
        });
//
//        addVideo.setOnClickListener(v -> {
//            Intent galleryIntent = new Intent();
//            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//            galleryIntent.setType("video/*");
//            startActivityForResult(galleryIntent, GalleryPick);
//        });

    }

    private void SendUserToSameActivity() {
        finish();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
//            imageUri = data.getData();
//            displayPostImage.setImageURI(imageUri);
//
//            /* Boolean value set to true after retrieving image */
//            if (imageUri == null) {
//                displayPostImage.setVisibility(View.INVISIBLE);
//            }
//            if (!(imageUri == null)) {
//                displayPostImage.setVisibility(View.VISIBLE);
//            }
//
//        }
//
//    }

    private void SavePosts() {
        PostWriteUp = postWriteUp.getText().toString();
        PostHeading = postHeading.getText().toString();

        if (imageUri2 == null) {
            Toast.makeText(CreateWriteUpActivity.this, "Select a valid Image", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(PostWriteUp)) {
            Toast.makeText(CreateWriteUpActivity.this, "Enter Post", Toast.LENGTH_LONG).show();
        } if (TextUtils.isEmpty(PostHeading)) {
            Toast.makeText(CreateWriteUpActivity.this, "Enter Head", Toast.LENGTH_LONG).show();
        }
//        if (CelebrationPicked.contains("#Select_Your_Celebration")) {
//            Toast.makeText(CreateWriteUpActivity.this, "Please Select Your Celebration", Toast.LENGTH_SHORT).show();
//            mProgress.dismiss();
//        }

        else {
            mProgress.setTitle("Posting");
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCanceledOnTouchOutside(false);

            StoringImageToFireBaseStorage();

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri2 = data.getData();


            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(4, 3)
                    .start(CreateWriteUpActivity.this);

//            displayPostImage.setOnClickListener(v->
//            {
//                Intent profileIntent = new Intent(CreateWriteUpActivity.this, ProfileImageViewerActivity.class);
//                profileIntent.putExtra("url", data.getData());
//                startActivity(profileIntent);
//            });

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            imageUri2 = result.getUri();
            displayPostImage.setImageURI(imageUri2);

            displayPostImage.setOnClickListener(v->
            {
//                Intent profileIntent = new Intent(CreateWriteUpActivity.this, ProfileImageViewerActivity.class);
//                profileIntent.putExtra("url", result.getUri().toString());
//                startActivity(profileIntent);
            });

//
//            /* Boolean value set to true after retrieving image */
//            if (imageUri == null) {
//                displayPostImage.setVisibility(View.INVISIBLE);
//            }
//            if (!(imageUri == null)) {
//                displayPostImage.setVisibility(View.VISIBLE);
//            }


        }

    }


    private void StoringImageToFireBaseStorage() {

        /* get current date */
        Calendar callforDate = Calendar.getInstance();
        SimpleDateFormat CurrentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        saveCurrentDate = CurrentDate.format(callforDate.getTime());

        /* get current time */
        Calendar callforTime = Calendar.getInstance();
        SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        saveCurrentTime = CurrentTime.format(callforTime.getTime());

        PostRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = postRefImages.child(imageUri2.getLastPathSegment() + PostRandomName + ".jpg");


        filePath.putFile(imageUri2).addOnSuccessListener(taskSnapshot -> {
            final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
            firebaseUri.addOnSuccessListener(uri -> {
                final String downloadUrl = uri.toString();

                postRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            countPost = dataSnapshot.getChildrenCount();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                UserRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String postId = FirebaseAuth.getInstance().getUid();
                            final String fullName = dataSnapshot.child("fullname").getValue(String.class);
                            final String ProfileImage = dataSnapshot.child("profileImage").getValue(String.class);

                            HashMap<String, Object> PostsImageMap = new HashMap<>();
                            PostsImageMap.put("uid", current_user_id);
                            PostsImageMap.put("date", saveCurrentDate);
                            PostsImageMap.put("time", saveCurrentTime);
                            PostsImageMap.put("writeUp", PostWriteUp);
                            PostsImageMap.put("heading", PostHeading);
                            PostsImageMap.put("postImage", downloadUrl);
                            PostsImageMap.put("profileImage", ProfileImage);
                            PostsImageMap.put("username", fullName);
                            PostsImageMap.put("Counter", countPost++);
                            PostsImageMap.put("postid", postId + PostRandomName);
//                            PostsImageMap.put("celebration", CelebrationPicked);

                            postRef.child(current_user_id + PostRandomName).updateChildren(PostsImageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    mProgress.dismiss();
                                    postWriteUp.setText("");
                                    Toast.makeText(CreateWriteUpActivity.this, "Posted", Toast.LENGTH_LONG).show();
                                    SendUserToSameActivity();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            });
        }).addOnProgressListener(taskSnapshot -> {

        });


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.menu = menu;
//        getMenuInflater().inflate(R.menu.create_post_menu, menu);
//        menu.getItem(0).setVisible(true);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.post) {
//
//            PostWriteUp = postWriteUp.getText().toString();
//
//            if (imageUri == null) {
//                Toast.makeText(CreateWriteUpActivity.this, "Select a valid Image", Toast.LENGTH_LONG).show();
//            }
//            if (TextUtils.isEmpty(PostWriteUp)) {
//                Toast.makeText(CreateWriteUpActivity.this, "Enter Text", Toast.LENGTH_LONG).show();
//            }
//            if (CelebrationPicked.contains("Select Your Celebration")) {
//                Toast.makeText(CreateWriteUpActivity.this, "Please Select Your Celebration", Toast.LENGTH_SHORT).show();
//                mProgress.dismiss();
//            } else {
//                mProgress.setTitle("Posting");
//                mProgress.setMessage("Please wait");
//                mProgress.show();
//                mProgress.setCanceledOnTouchOutside(false);
//
//
//                StoringImageToFireBaseStorage();
//
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }




    @Override
    protected void onStart() {
        super.onStart();
        updateUserStatus("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateUserStatus("offline");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUserStatus("online");
    }


    private void updateUserStatus(String state) {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a", Locale.US);
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);

        current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DatabaseReference statuede = FirebaseDatabase.getInstance().getReference().child("State");

        statuede.child(current_user_id).child("onlineState").updateChildren(onlineStateMap);
    }


}