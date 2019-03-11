package com.mkyong.config;

import com.mkyong.config.model.Menu;
import com.mkyong.config.model.Server;
import com.mkyong.config.model.Theme;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("wordpress")
public class WordpressProperties {

    private List<Menu> menus = new ArrayList<>();
    private Theme themes;
    private List<Server> servers = new ArrayList<>();

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Theme getThemes() {
        return themes;
    }

    public void setThemes(Theme themes) {
        this.themes = themes;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "WordpressProperties{" +
                "menus=" + menus +
                ", themes=" + themes +
                ", servers=" + servers +
                '}';
    }
}
