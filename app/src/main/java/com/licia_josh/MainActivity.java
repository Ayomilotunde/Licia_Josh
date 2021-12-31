package com.licia_josh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.licia_josh.models.Post;
import com.licia_josh.viewHolder.PostRecyclerViewHolder;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference UserDatabase, VidPostRef, PostRef, LikesRef, PostRefs, TextRef, VIPPaymentRef, UserDataBaseVip;

    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post, PostRecyclerViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        init();
    }


    void init() {
//        mAuth = FirebaseAuth.getInstance();
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        VidPostRef = FirebaseDatabase.getInstance().getReference().child("VideoPosts");
        UserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
//        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        VIPPaymentRef = FirebaseDatabase.getInstance().getReference().child("VIPPaymentProfile");


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutmanager = new LinearLayoutManager(this);
        linearLayoutmanager.setReverseLayout(true);
        linearLayoutmanager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutmanager);

        loadImageData();

    }


    private void loadImageData() {
        PostRef.keepSynced(true);
        Query sortPostInDescendingOrder = PostRef.orderByChild("Counter");
//                .orderByChild("Counter");
        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(sortPostInDescendingOrder, Post.class).build();
        adapter = new FirebaseRecyclerAdapter<Post, PostRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final PostRecyclerViewHolder postViewHolder, final int i, @NonNull final Post post) {
                /*added username and piccaso for profile image here*/
                final String PostKey = getRef(i).getKey();




//                if (post.getPostVideo() != null) {
//                    postViewHolder.setExoplayer(getActivity(), post.getPostVideo());
//                    postViewHolder.videoView.setVisibility(View.VISIBLE);
//                    postViewHolder.postImage.setVisibility(View.GONE);
//                }
//                else if (post.getPostImage() != null)
//                {
//
//                    Picasso.get()
//                            .load(post.getPostImage())
//                            .placeholder(R.drawable.loadingbuffering)
//                            .into(postViewHolder.postImage);
//                    postViewHolder.postImage.setVisibility(View.VISIBLE);
//                    postViewHolder.videoView.setVisibility(View.GONE);
//                }
//                else {
                    Picasso.get()
                            .load(post.getPostImage())
//                            .placeholder(R.drawable.loadingbuffering)
                            .into(postViewHolder.postImage);
//                }


                UserDatabase.child(post.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.exists();
                        String ppix = snapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(ppix).placeholder(R.drawable.ic_person).into(postViewHolder.postProfileImage);
//                        Picasso.get()
//                                .load(ppix)
//                                .placeholder(R.drawable.ic_person)
//                                .into(postViewHolder.postProfileImage2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                postViewHolder.username.setText(post.getUsername());
                postViewHolder.postWriteUp.setText(post.getWriteUp());
                postViewHolder.celebration.setText(post.getHeading());
                postViewHolder.postDate.setText(post.getDate() + "  ");
                postViewHolder.postTime.setText(post.getTime());
//                postViewHolder.commentUsername.setText(post.getUsername());
//                postViewHolder.celebration.setText(post.getCelebration());
//                postViewHolder.comment.setText(post.getWriteUp());

//
/*

                postViewHolder.setLikesButtonStatus(PostKey);
//                postViewHolder.setCommentButtonStatus(PostKey);

                postViewHolder.giftViewButton.setOnClickListener(v -> {

                    postViewHolder.giftViewButton.setText("MY" + "\n" + "Gifts");

                    Intent profileIntent = new Intent(getActivity(), IndividualGiftViewActivity.class);
                    profileIntent.putExtra("Visit_User_ID", PostKey);
                    startActivity(profileIntent);


//                    startActivity(new Intent(getActivity(), IndividualGiftViewActivity.class));
                });


               *//* postViewHolder.postProfileImage.setOnClickListener(v -> {
//                   final String visit_user_id = getRef(i).getKey();

                    Intent profileIntent = new Intent(getActivity(), PersonProfileActivity.class);
                    profileIntent.putExtra("Visit_User_ID", PostKey);
                    startActivity(profileIntent);

                });*//*


                postViewHolder.postImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent clickPostIntent = new Intent(getActivity(), ViewPostClickedActivity.class);
                        clickPostIntent.putExtra("PostKey", PostKey);
                        startActivity(clickPostIntent);


                    }
                });

                PostRefs = FirebaseDatabase.getInstance().getReference("Comments").child(PostKey);
                PostRefs.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        postViewHolder.NoOfComment.setText("view all " + dataSnapshot.getChildrenCount() + " comment(s)");
//                        //postViewHolder.NoOfComment.setText("Add Comment(s)...");


                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        final String user = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        int countComments = (int) dataSnapshot.getChildrenCount();

                        postViewHolder.NoOfComment.setText((countComments + (" Comment(s)")));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                postViewHolder.CommentButton.setOnClickListener(view -> {
                    Intent CommentIntent = new Intent(getActivity(), ViewAllCommentsActivity.class);
                    CommentIntent.putExtra("PostKey", PostKey);
                    startActivity(CommentIntent);


                });
                postViewHolder.NoOfComment.setOnClickListener(view -> {

                    Intent CommentIntent = new Intent(getActivity(), AllCommentsActivity.class);
                    CommentIntent.putExtra("PostKey", PostKey);
                    startActivity(CommentIntent);
                });

                postViewHolder.shareButton.setOnClickListener(v -> {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Download BigDay mobile app where you can view your friends celebration and share gifts with your friends." +
                            "   " + "\n" +
                            "Click to download - " + "App Link here";
                    String shareSubject = "Your Subject Here";

                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                    startActivity(Intent.createChooser(sharingIntent, "Share Your Friend Using"));
                });


                postViewHolder.GiftButton.setOnClickListener(v -> {


                    Intent profileIntent = new Intent(getActivity(), GiftShopActivity.class);
                    profileIntent.putExtra("Visit_User_ID", PostKey);
                    startActivity(profileIntent);

                });


                postViewHolder.TatcoinButton.setOnClickListener(v -> {

                    Intent profileIntent = new Intent(getActivity(), GiftShopActivity.class);
                    profileIntent.putExtra("Visit_User_ID", PostKey);
                    startActivity(profileIntent);

                });


                postViewHolder.LikeButton.setOnClickListener(view -> {
                    LikeChecker = true;

                    currentUserID = mAuth.getCurrentUser().getUid();

                    LikesRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (LikeChecker.equals(true)) {
                                if (dataSnapshot.child(PostKey).hasChild(currentUserID)) {
                                    LikesRef.child(PostKey).child(currentUserID).removeValue();
                                    LikeChecker = false;
                                    addNotification2(post.getUid(), post.getPostid());


                                } else {
                                    LikesRef.child(PostKey).child(currentUserID).setValue(true);
                                    LikeChecker = false;


                                    addNotification(post.getUid(), post.getPostid());

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                });*/


            }

            @NonNull
            @Override
            public PostRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                /* set and inflate the layout needed for displaying data */

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_item, parent, false);

                PostRecyclerViewHolder viewHolder = new PostRecyclerViewHolder(view);
                return viewHolder;

//                return new PostRecyclerViewHolder(view);


            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

}