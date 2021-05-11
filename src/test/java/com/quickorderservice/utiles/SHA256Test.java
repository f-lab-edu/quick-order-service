package com.quickorderservice.utiles;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256Test {

    @Test
    void shaTest() throws Exception {
        String en1 =SHA256.encBySha256("1234!");
        String en2 =SHA256.encBySha256("1234!");
        String en3 =SHA256.encBySha256("12345");

        Assertions.assertThat(en1).isEqualTo(en2);
        Assertions.assertThat(en1).isNotEqualTo(en3);
    }

}