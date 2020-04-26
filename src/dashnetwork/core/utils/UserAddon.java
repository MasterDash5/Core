package dashnetwork.core.utils;

public class UserAddon {

    public UserAddon(User user) {
        user.addAddon(this);
    }

}
