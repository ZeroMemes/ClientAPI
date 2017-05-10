package me.zero.client.api.exception;

/**
 * Called when a {@code ClassWrapper} runs into an
 * issue that cannot be resolved immediately.
 *
 * @since 1.0
 *
 * @author Brady
 * @since 4/8/2017 12:00PM
 */
public final class ClassWrapperException extends RuntimeException {

    public ClassWrapperException(String message) {
        super(message);
    }
}
