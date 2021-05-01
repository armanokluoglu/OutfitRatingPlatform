package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;
import view.AllUsersFrame;
import view.HomeFrame;

public class AllUsersController implements Observer {

    private SessionManager session;
    private AllUsersFrame view;
    private List<Collection> collections;
    private Subject model;
    private List<User> users;

    public AllUsersController(Model model, AllUsersFrame view, SessionManager session) {
        this.session = session;
        this.view = view;
        this.model = model;

        model.register(view);

        this.collections = model.getCollectionsOfFollowingsOfUserChronologically(session.getCurrentUser());
        this.users = model.getAllUsers();

        setSidebarListeners();
        setContentListeners();
    }

    private void setSidebarListeners() {
        view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
        view.addLogoutActionListener(new LogoutListener());
        view.addOutfitsActionListener(new OpenOutfitsListener());
        view.addStatisticsActionListener(new OpenStatisticsListener());
    }

    private void setContentListeners() {
        for (Collection collection : collections) {
            view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
            view.addOpenUserActionListener(new OpenUserListener(collection.getCreator()), collection.getCreator().getUsername());
        }
        for (User user : users) {
            view.addOpenUserActionListener(new OpenUserListener(user), user.getUsername());
        }
    }

    @Override
    public void update() {
        setContentListeners();
    }

    @Override
    public void addSubject(Subject sub) {
        this.model = sub;
    }

    @Override
    public void removeSubject(Subject sub) {
        this.model = null;

    }


    class OpenUserListener implements ActionListener {
        private User user;

        public OpenUserListener(User user) {
            this.user = user;
        }

        public void actionPerformed(ActionEvent e) {
            session.userPage(user);
        }
    }

    class OpenCollectionListener implements ActionListener {
        private Collection collection;

        public OpenCollectionListener(Collection collection) {
            this.collection = collection;
        }

        public void actionPerformed(ActionEvent e) {
            session.collectionPage(collection);
        }
    }

    class OpenStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            session.statisticsPage();
        }
    }

    class OpenOutfitsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            session.outfitsPage();
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            session.loginPage();
        }
    }
}
