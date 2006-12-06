package edu.ucsd.itd.mines.service;

/**
 * Simple implementation of the registry of services. Exists as a Singleton.
 * The MinesService should be set either after an instance is retrieved or
 * through dependency injection.
 */
public class ServiceLocatorImpl implements ServiceLocator {
    private static ServiceLocatorImpl _instance = null;
    private MinesService minesService;

    private ServiceLocatorImpl() { }

    public static void init() {
        if(_instance == null)
            _instance = new ServiceLocatorImpl();
    }

    public static ServiceLocatorImpl getInstance() {
        if(_instance != null)
            return _instance;
        else {
            init();
            return _instance;
        }

    }

    public MinesService getMinesService() {
        return minesService;
    }

    public void setMinesService(MinesService minesService) {
        this.minesService = minesService;
    }
}
