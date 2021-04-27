package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import view.AllUsersFrame;
import view.HomeFrame;

public class AllUsersController {

    private SessionManager session;
    private AllUsersFrame view;
    private List<Collection> collections;
    private List<User> users;

    public AllUsersController(Model model, AllUsersFrame view, SessionManager session) {
        this.session = session;
        this.view = view;

        view.addSubject(model);
        model.register(view);

        this.collections = model.getCollectionsOfFollowingsOfUserChronologically(session.getCurrentUser());
        this.users = model.getAllUsers();
        view.setCards();

        setSidebarListeners();
        setContentListeners();
    }

    private void setSidebarListeners() {
        view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
        view.addLogoutActionListener(new LogoutListener());
        view.addOutfitsActionListener(new OpenOutfitsListener());
        view.addAllUsersActionListener(new OpenAllUserListener(session.getCurrentUser()));
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
    class OpenAllUserListener implements ActionListener {
        private User user;

        public OpenAllUserListener(User user) {
            this.user = user;
        }

        public void actionPerformed(ActionEvent e) {
            session.allUsersPage(user);
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            session.loginPage();
        }
    }
}
