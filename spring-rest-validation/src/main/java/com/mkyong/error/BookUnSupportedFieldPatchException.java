package com.mkyong.error;

import java.util.Set;

public class BookUnSupportedFieldPatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BookUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }

}
