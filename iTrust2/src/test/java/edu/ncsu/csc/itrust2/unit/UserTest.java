package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * Unit tests for the User class.
 *
 * @author jshore
 *
 */
public class UserTest {

    /**
     * Tests equals comparison of two user objects. Also verifies getters and
     * setters of the used properties.
     */
    @Test
    public void testEqualsAndProperties () {
        final User u1 = new User();
        final User u2 = new User();
        final User u3 = new User();

        assertFalse( u1.equals( new Object() ) );
        assertFalse( u1.equals( null ) );
        assertTrue( u1.equals( u1 ) );

        u1.setEnabled( 1 );
        assertTrue( 1 == u1.getEnabled() );
        u2.setEnabled( 1 );
        u3.setEnabled( 1 );

        u1.setPassword( "abcdefg" );
        assertEquals( "abcdefg", u1.getPassword() );
        u2.setPassword( "abcdefg" );
        u3.setPassword( "12345" );

        u1.setRole( Role.valueOf( "ROLE_PATIENT" ) );
        assertEquals( Role.valueOf( "ROLE_PATIENT" ), u1.getRole() );
        u2.setRole( Role.valueOf( "ROLE_PATIENT" ) );
        u3.setRole( Role.valueOf( "ROLE_HCP" ) );

        u1.setUsername( "abcdefg" );
        assertEquals( "abcdefg", u1.getUsername() );
        u2.setUsername( "abcdefg" );
        u3.setUsername( "iamatestuser" );

        assertTrue( u1.equals( u2 ) );

        // test when Users don't equal each other
        assertFalse( u1.equals( u3 ) );

        assertTrue( User.getPatients().size() != 0 );
        assertTrue( User.getLabtechs().size() != 0 );
        assertTrue( User.getHCPs().size() != 0 );
        assertTrue( User.getByRole( Role.ROLE_PATIENT ).size() != 0 );
    }

    /**
     * Tests equals() and hashCode()
     */
    @Test
    public void testEqualsAndHashCode () {
        final User u1 = new User();
        final User u2 = new User();

        u2.setEnabled( null );
        u1.setEnabled( null );
        assertTrue( u1.equals( u2 ) );
        assertTrue( u1.hashCode() == u2.hashCode() );
        u2.setEnabled( 1 );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u1.setEnabled( 1 );
        u2.setEnabled( null );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u2.setEnabled( 0 );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u1.setEnabled( 0 );

        u1.setPassword( null );
        u2.setPassword( null );
        assertTrue( u1.equals( u2 ) );
        assertTrue( u1.hashCode() == u2.hashCode() );
        u2.setPassword( "123" );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u2.setPassword( "123" );
        u1.setPassword( null );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u1.setPassword( "234" );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u1.setPassword( "123" );

        u1.setRole( Role.ROLE_ADMIN );
        u2.setRole( Role.ROLE_ER );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u1.setRole( Role.ROLE_ER );

        u1.setUsername( null );
        u2.setUsername( null );
        assertTrue( u1.equals( u2 ) );
        assertTrue( u1.hashCode() == u2.hashCode() );
        u2.setUsername( "s" );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u2.setUsername( null );
        u1.setUsername( "s" );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
        u2.setUsername( "s2" );
        assertFalse( u1.equals( u2 ) );
        assertTrue( u1.hashCode() != u2.hashCode() );
    }
}
