package app.details.listing.intcore.george.mina.listingdetailsapp.ui.listingActivity;

import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;

public interface ListingActivityVP {

    interface View{

        void startLoading();
        void loadingFinished(ViewModel model);
        void openDetailsActivity(int pos,ViewModel model);
        void showMessage(int x);
    }

    interface Presenter{

        void setView(ListingActivityVP.View view);
        void loadData();
        void rxUnsubscribe();
    }
}
