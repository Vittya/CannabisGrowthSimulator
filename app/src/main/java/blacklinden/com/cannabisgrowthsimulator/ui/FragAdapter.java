package blacklinden.com.cannabisgrowthsimulator.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.annotation.Nullable;

public class FragAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public FragAdapter(FragmentManager fm) {
        super(fm);

        fragments = new Fragment[]{
                new Lights(),
                new Nutrition(),
                new Accessories(),
                new Skins()
        };
    }

    public FragAdapter(FragmentManager fm, @Nullable String s){
        super(fm);
        fragments = new Fragment[]{
                new ComboFragment()
        };
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String cím  = getItem(position).getClass().getName();
        return cím.subSequence(cím.lastIndexOf(".")+1,cím.length());
    }
}
