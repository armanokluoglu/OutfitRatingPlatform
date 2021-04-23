package controller;

import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import view.CollectionFrame;
import view.FrameManager;
import view.HomeFrame;
import view.LoginFrame;
import view.OutfitFrame;
import view.OutfitsFrame;
import view.StatisticsFrame;
import view.UserFrame;

public class SessionManager {
    private User currentUser;
    private FrameManager fm;
    private Model model;

    public SessionManager(User currentUser, FrameManager fm, Model model) {
        this.setCurrentUser(currentUser);
        this.fm = fm;
        this.model = model;
    }
    
    public void loginPage() {
    	LoginFrame loginView = new LoginFrame(fm);
    	LoginController loginController = new LoginController(model, loginView, this);
    }
    
    public void homePage() {
    	HomeFrame homeView = new HomeFrame(model, fm);
    	HomeController homeController = new HomeController(model, homeView, this);
    }
    
    public void userPage(User user) {
    	UserFrame userView = new UserFrame(model, fm);
    	UserController userController = new UserController(model, userView, this, user);
    }
    
    public void collectionPage(Collection collection) {
    	CollectionFrame collectionView = new CollectionFrame(model, fm);
    	CollectionController collectionController = new CollectionController(model, collectionView, this, collection);
    }
    
    public void outfitPage(Outfit outfit) {
    	OutfitFrame outfitView = new OutfitFrame(model, fm);
    	OutfitController outfitController = new OutfitController(model, outfitView, this, outfit);
    }

    public void outfitsPage() {
    	OutfitsFrame outfitsView = new OutfitsFrame(model, fm);
    	OutfitsController outfitsController = new OutfitsController(model, outfitsView, this);
    }
    
    public void statisticsPage() {
    	StatisticsFrame statisticsView = new StatisticsFrame(model, fm);
    	StatisticsController statisticsControler = new StatisticsController(model, statisticsView, this);
    }
    
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

//    public void openMainMenu(){
//        MenuView menuView = new MenuView(frame);
//        MenuController menuController = new MenuController(menuView, this);
//    }
//
//    public void openBrowseUsers(){
//        UserDataHandler dataHandler = new UserDataHandler();
//        BrowseUsersView browseUsersView = new BrowseUsersView(frame, dataHandler.getUsernames(), user);
//        BrowseUsersController browseUsersController = new BrowseUsersController(browseUsersView, this);
//    }
//
//    public void openBrowseWatchlists(){
//        BrowseWatchlistView browseWatchlistView = new BrowseWatchlistView(frame, user);
//        BrowseWatchlistController browseWatchlistController = new BrowseWatchlistController(browseWatchlistView, this);
//    }
//
//    public void openBrowseAllVideos(){
//        BrowseVideosView browseVideosView = new BrowseVideosView(frame);
//        BrowseVideosController browseVideosController = new BrowseVideosController(browseVideosView, this);
//    }
//
//    public void openWatchlist(Watchlist watchlist){
//        WatchlistView watchlistView = new WatchlistView(frame, watchlist);
//        WatchlistController watchlistController = new WatchlistController(watchlistView, watchlist, this);
//    }
//
//    public void openVideo(Video video, Watchlist watchlist){
//        VideoView videoView = new VideoView(frame, video);
//        VideoController videoController = new VideoController(video, videoView, watchlist, this);
//    }
//
//    public void logout(){
//        LoginView loginView = new LoginView(frame);
//        LoginController loginController = new LoginController(loginView);
//    }

}