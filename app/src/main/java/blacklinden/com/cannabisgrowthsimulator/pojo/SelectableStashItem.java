package blacklinden.com.cannabisgrowthsimulator.pojo;

public class SelectableStashItem  extends Stash{
        private boolean isSelected = false;
        private Stash stash;


        public SelectableStashItem(Stash stash,boolean isSelected) {
            super(stash.getId(),stash.getFajta(),stash.getMinőség(),stash.getMennyi());
            this.isSelected = isSelected;
            this.stash=stash;
        }


        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public Stash getStash(){return stash;}
    }

