package xyz.yarinlevi.thedestroyer.config;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private int configVersion = 1;
    private Yaml yaml = new Yaml();
    private File configFile = null;
    private Map<String, Object> result = new HashMap<>();

    /**
     * Initialize the configuration
     *
     * @param config
     *            Config version
     */
    public Configuration(String filePath, int config) {
        this.configVersion = config;
        System.out.println("Configuration version: " + configVersion);
        this.loadConfig(filePath);
    }

    @SuppressWarnings("unchecked")
    public void loadConfig(String filePath) {
        System.out.println("Loading configuration ...");
        configFile = new File(filePath);
        if (!configFile.exists()) {
            // Create file
            try {
                InputStream jarURL = this.getClass().getResourceAsStream(
                        "/" + filePath);
                if (jarURL != null) {
                    System.out.println("Copying '" + configFile
                            + "' from the resources!");
                    copyFile(jarURL, configFile);
                } else {
                    System.out.println("Configuration file not found inside the application!");
                }
                jarURL.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (configFile.exists()) {
            // Load the configuration file
            try {
                InputStream ios = new FileInputStream(configFile);
                result = (Map<String, Object>) yaml.load(ios);
                ios.close();
                if (getInt("config") != this.configVersion) {
                    // Configuration version mismatch
                    System.out.println("Config version does not match!");
                }
            } catch (FileNotFoundException e) {
                // Error while loading the file
            } catch (IOException e) {
                // Error while closing
            }
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    @SuppressWarnings("unchecked")
    public Object get(String path) {
        String[] pathArr = path.split("\\.");
        if (pathArr.length == 0) {
            pathArr = new String[1];
            pathArr[0] = path;
        }
        Object lastObj = this.result;
        for (int i = 0; i < pathArr.length; i++) {
            lastObj = ((Map<String, Object>) lastObj).get(pathArr[i]);
        }
        return lastObj;
    }

    /**
     * Get boolean value from configuration
     *
     * @param path
     *            Configuration path
     * @return Boolean value
     */
    public boolean getBoolean(String path) {
        Object lastObj = get(path);
        if (lastObj instanceof Boolean)
            return (Boolean) lastObj;
        return false;
    }

    /**
     * Get integer value from configuration
     *
     * @param path
     *            Configuration path
     * @return Integer result
     */
    public int getInt(String path) {
        Object lastObj = get(path);
        if (lastObj instanceof Integer)
            return (Integer) lastObj;
        return 0;
    }

    /**
     * Get string value from configuration
     *
     * @param path
     *            Configuration path
     * @return String result
     */
    public String getString(String path) {
        Object lastObj = get(path);
        if (lastObj instanceof String)
            return (String) lastObj;
        return "";
    }

    /**
     * Copies a file to a new location.
     *
     * @param in
     *            InputStream
     * @param out
     *            File
     *
     * @throws Exception
     */
    static void copyFile(InputStream in, File out) throws Exception {
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
