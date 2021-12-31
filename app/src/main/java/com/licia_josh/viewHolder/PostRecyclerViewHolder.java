package com.licia_josh.viewHolder;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

//import com.facebook.shimmer.ShimmerFrameLayout;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.extractor.ExtractorsFactory;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.licia_josh.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecyclerViewHolder extends RecyclerView.ViewHolder {

//    SimpleExoPlayer exoPlayer;

    Animation translate_anim;

//    public ShimmerFrameLayout shimmerFrameLayout;
    public ConstraintLayout  rootPersonals;
    public LinearLayout  rootPersonal;
    public ConstraintLayout  rootPersonal1, main;
    public ConstraintLayout root;
//    public PlayerView videoView;
    public TextView username, postWriteUp, postDate, postTime, commentUsername, comment,celebration, giftViewButton, trendPost;
    public ImageView postImage, postProfileImage,  postProfileImage2, VIPAdd, VIPBadge, VIPProofImage;
    public ImageView personalPostImage;
    public ImageButton LikeButton, CommentButton, shareButton, GiftButton, TatcoinButton;
    public TextView NoOfLikes, NoOfComment, ItemName, ItemPrice, vipFullName, vipUID;
    int countNoOfLikes, countComments;
    private FirebaseAuth mAuth;
    String currentUserID,post_key ;
    DatabaseReference LikesRef, PostRef;
    private DatabaseReference PostRefs;

    boolean isShimmer = false;
    int Shimmernumber = 5;

    public PostRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
//        LikeButton = itemView.findViewById(R.id.dislikeButtom1);
//        CommentButton = itemView.findViewById(R.id.commentButton1);
//        NoOfLikes = itemView.findViewById(R.id.displayLikes1);
//        NoOfComment = itemView.findViewById(R.id.comments1);
        mAuth = FirebaseAuth.getInstance();



        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");

//        rootPersonals = itemView.findViewById(R.id.personal_list_roots);
//        rootPersonal = itemView.findViewById(R.id.personal_list_root);
////        rootPersonal1 = itemView.findViewById(R.id.personal1_list_root);
//        main = itemView.findViewById(R.id.main);
//        personalPostImage = itemView.findViewById(R.id.personal_post_image);
//        ItemName = itemView.findViewById(R.id.itemName);
//        ItemPrice = itemView.findViewById(R.id.itemPrice);
//        trendPost = itemView.findViewById(R.id.post_celebration);

        root = itemView.findViewById(R.id.list1_root);
        username = itemView.findViewById(R.id.Post_user_name1);
        celebration = itemView.findViewById(R.id.Post_celebration);
        postWriteUp = itemView.findViewById(R.id.description1);
        postDate = itemView.findViewById(R.id.Post_date1);
        postTime = itemView.findViewById(R.id.Post_time1);
        postImage = itemView.findViewById(R.id.Post_image);
//        videoView = itemView.findViewById(R.id.exo_player);
        postProfileImage = itemView.findViewById(R.id.Post_Profile_Image1);
//        postProfileImage2 = itemView.findViewById(R.id.Post_Profile_Image2);
//        commentUsername = itemView.findViewById(R.id.usernameComment);
//        TatcoinButton = itemView.findViewById(R.id.tatcoinButton);
////        comment = itemView.findViewById(R.id.comments1);
//        shareButton = itemView.findViewById(R.id.shareButton1);
//        GiftButton = itemView.findViewById(R.id.donateButton1);
//        giftViewButton = itemView.findViewById(R.id.giftViewButton);
//        VIPAdd = itemView.findViewById(R.id.VIP_add);
//        VIPBadge = itemView.findViewById(R.id.vipBadge);
//        vipFullName = itemView.findViewById(R.id.vipFullName);
//
//        vipUID = itemView.findViewById(R.id.vipUid);
//        VIPProofImage = itemView.findViewById(R.id.vipProoof_image);

//        shimmerFrameLayout = itemView.findViewById(R.id.shimmereffect);

//        /*Animate Recycler View*/
//        translate_anim = AnimationUtils.loadAnimation(main.getContext(), R.anim.translation_anim);
//        main.setAnimation(translate_anim);


    }

//    public void setExoplayer(Activity application, String videoUri) {
//        try {
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(application).build();
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//            exoPlayer = ExoPlayerFactory.newSimpleInstance(application);
//            Uri video = Uri.parse(videoUri);
//            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("postVideo");
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);
//            videoView.setPlayer(exoPlayer);
//            exoPlayer.prepare(mediaSource);
//            exoPlayer.setPlayWhenReady(false);
//        } catch (Exception e) {
//
//        }
//    }
//
//    public void setLikesButtonStatus(final String postKey)
//    {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final String user = mAuth.getCurrentUser().getUid();
//
//        LikesRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//
//
//
//                if (dataSnapshot.child(postKey).child(user).exists())
//                {
//                    countNoOfLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
//                    NoOfLikes.setText((countNoOfLikes + (" Likes")));
//                    LikeButton.setImageResource(R.drawable.like);
//
//
//                }
//                else
//                {
//                    countNoOfLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
//                    NoOfLikes.setText((countNoOfLikes + (" Likes")));
//                    LikeButton.setImageResource(R.drawable.dislike);
//
//
//
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
