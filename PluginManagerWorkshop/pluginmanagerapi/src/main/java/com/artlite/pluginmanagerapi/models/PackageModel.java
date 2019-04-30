package com.artlite.pluginmanagerapi.models;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Class which provide the model for the installed package info
 */
public final class PackageModel implements Parcelable {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PackageModel.class.getSimpleName();

    /**
     * {@link String} value of the application name
     */
    protected String applicationName;

    /**
     * {@link String} value of the package name
     */
    protected String packageName;

    /**
     * {@link String} value of the application version
     */
    protected String version;

    /**
     * {@link Integer} value of the application version code
     */
    protected int versionCode;

    /**
     * Instance of the {@link Drawable}
     */
    protected Bitmap icon;

    /**
     * {@link Boolean} value if it enabled
     */
    protected boolean isEnabled = false;

    /**
     * Default constructor
     */
    public PackageModel() {
    }

    /**
     * Method which provide the equaling of the objects
     *
     * @param o instance of the {@link Object}
     * @return equaling result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageModel that = (PackageModel) o;
        if (versionCode != that.versionCode) return false;
        if (applicationName != null
                ? !applicationName.equals(that.applicationName)
                : that.applicationName != null)
            return false;
        if (packageName != null
                ? !packageName.equals(that.packageName)
                : that.packageName != null)
            return false;
        if (version != null
                ? !version.equals(that.version)
                : that.version != null) return false;
        return icon != null
                ? icon.equals(that.icon)
                : that.icon == null;
    }

    /**
     * Method which provide the hash code for the {@link PackageModel}
     *
     * @return {@link Integer} value of the hash code
     */
    @Override
    public int hashCode() {
        int result = applicationName != null
                ? applicationName.hashCode() : 0;
        result = 31 * result + (packageName != null
                ? packageName.hashCode() : 0);
        result = 31 * result + (version != null
                ? version.hashCode() : 0);
        result = 31 * result + versionCode;
        result = 31 * result + (icon != null
                ? icon.hashCode() : 0);
        return result;
    }

    /**
     * Method which provide the describe contents
     *
     * @return {@link Integer} value of the described content
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method which provide the write to the parcel
     *
     * @param dest  instance of the {@link Parcel}
     * @param flags {@link Integer} value of the flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.applicationName);
        dest.writeString(this.packageName);
        dest.writeString(this.version);
        dest.writeInt(this.versionCode);
        dest.writeParcelable(this.icon, flags);
    }

    /**
     * Parcelable constructor
     *
     * @param in instance of the {@link Parcel}
     */
    protected PackageModel(Parcel in) {
        this.applicationName = in.readString();
        this.packageName = in.readString();
        this.version = in.readString();
        this.versionCode = in.readInt();
        this.icon = in.readParcelable(Drawable.class.getClassLoader());
    }

    /**
     * Instance of the {@link Creator}
     */
    public static final Creator<PackageModel> CREATOR
            = new Creator<PackageModel>() {
        @Override
        public PackageModel createFromParcel(Parcel source) {
            return new PackageModel(source);
        }

        @Override
        public PackageModel[] newArray(int size) {
            return new PackageModel[size];
        }
    };

    /**
     * Method which provide the getting the instance of the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param applicationName instance of the {@link Object}
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * Method which provide the getting the instance of the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param packageName instance of the {@link Object}
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Method which provide the getting the instance of the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public String getVersion() {
        return version;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param version instance of the {@link Object}
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Method which provide the getting the instance of the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param versionCode instance of the {@link Object}
     */
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * Method which provide the getting the instance of the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public Bitmap getIcon() {
        return icon;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param icon instance of the {@link Object}
     */
    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param icon instance of the {@link Object}
     */
    public void setIcon(Drawable icon) {
        try {
            this.icon = ((BitmapDrawable) icon).getBitmap();
        } catch (Exception ex) {
            Log.e(TAG, "setIcon: ", ex);
        }
    }

    /**
     * Method which provide the getting the instance of the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param enabled instance of the {@link Object}
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Builder for the {@link PackageModel}
     */
    public static final class Builder {

        /**
         * Instance of the {@link PackageModel}
         */
        private PackageModel model;

        /**
         * Default constructor
         */
        public Builder() {
            model = new PackageModel();
        }

        /**
         * Method which provide the setting of the {@link Object}
         *
         * @param val instance of the {@link Object}
         */
        public Builder setApplicationName(String val) {
            model.applicationName = val;
            return this;
        }

        /**
         * Method which provide the setting of the {@link Object}
         *
         * @param val instance of the {@link Object}
         */
        public Builder setPackageName(String val) {
            model.packageName = val;
            return this;
        }

        /**
         * Method which provide the setting of the {@link Object}
         *
         * @param val instance of the {@link Object}
         */
        public Builder setVersion(String val) {
            model.version = val;
            return this;
        }

        /**
         * Method which provide the setting of the {@link Object}
         *
         * @param val instance of the {@link Object}
         */
        public Builder setVersionCode(int val) {
            model.versionCode = val;
            return this;
        }

        /**
         * Method which provide the setting of the {@link Object}
         *
         * @param val instance of the {@link Object}
         */
        public Builder setIcon(Bitmap val) {
            model.icon = val;
            return this;
        }

        /**
         * Method which provide to build of the {@link PackageModel}
         *
         * @return instance of the {@link PackageModel}
         */
        public PackageModel build() {
            return model;
        }
    }
}
