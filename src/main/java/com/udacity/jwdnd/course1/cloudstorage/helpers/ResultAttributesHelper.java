package com.udacity.jwdnd.course1.cloudstorage.helpers;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ResultAttributesHelper {

    private ResultAttributesHelper() {}

    public static void setResultAttribute(int result, RedirectAttributes redirectAttributes) {
        if (result > 0) {
            redirectAttributes.addFlashAttribute("success", true);
        } else {
            redirectAttributes.addFlashAttribute("error", true);
        }
    }

}
