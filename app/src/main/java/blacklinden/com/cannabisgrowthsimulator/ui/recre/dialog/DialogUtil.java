package blacklinden.com.cannabisgrowthsimulator.ui.recre.dialog;

import blacklinden.com.cannabisgrowthsimulator.R;

class DialogUtil {


   static int setIcon(String icon) {

        switch (icon) {
            case "Skunk":
                return (R.drawable.ic_skunk_n1);

            case "Afghan":
                return (R.drawable.ic_afghan);

            case "Balkan Rose":
                return (R.drawable.ic_balkan_rose);

            case "BlueBerry":
                return (R.drawable.ic_blueberry_icon);

            case "Northern Light":
                return (R.drawable.ic_northern_light_icon);

            case "Grape Ape":
                return (R.drawable.ic_grape_ape_icon);

            case "AK47":
                return (R.drawable.ic_ak47);

            case "Cheese":
                return (R.drawable.ic_cheese_icon);

            case "Amnesia":
                return (R.drawable.ic_amnesia_icon);

            case "S.Lemon Haze":
                return (R.drawable.ic_super_lemon_haze);

            case "White Widow":
                return (R.drawable.ic_white_widow_icon);

            case "Gelato":
                return (R.drawable.ic_gelato_icon);

            case "Ghost OG":
                return (R.drawable.ic_ghost_og_icon);

            case "Cherry Diesel":
                return (R.drawable.ic_cherry_diesel_icon);

            case "Permafrost":
                return (R.drawable.ic_permafrost_icon);

            case "Pink Mango":
                return (R.drawable.ic_pink_mango_icon);

            case "Pineapple":
                return (R.drawable.ic_pineapple_express);

            case "G.White Shark":
                return (R.drawable.ic_gws_icon);

            case "Nurse R.":
                return (R.drawable.ic_nrs_icon);

            case "Durban":
                return (R.drawable.ic_durban_poison_icon);

            default:
                return R.drawable.ic_empty_icon;
        }
    }

    static String qualitytext(String status){
        switch (status){
            case "molded":
                return "Rotten Schwag";
            case "wet":
                return "Raw";
            case "smelly":
                return "Reggie Weed";
            case "good":
                return "Mid Grade";
            case "goldilocks":
                return "Premium Dank";
            default:return "Unknown";
        }
    }


    }
