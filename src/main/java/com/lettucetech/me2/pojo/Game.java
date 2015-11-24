package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer gameId;

    private String name;

    /**
     * 是否启用
     */
    private String del;

    /**
     * @return id
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * @param gameId 
	 *            id
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 是否启用
     */
    public String getDel() {
        return del;
    }

    /**
     * @param del 
	 *            是否启用
     */
    public void setDel(String del) {
        this.del = del;
    }
}