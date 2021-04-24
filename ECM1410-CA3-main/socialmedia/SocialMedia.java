package socialmedia;
import java.io.*;
import java.util.ArrayList;

public class SocialMedia implements SocialMediaPlatform {
    ArrayList<Account> accounts = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();

    public ArrayList<Post> getPosts() {
		return posts;
	}
    public ArrayList<Account> getAccounts() {
		return accounts;
	}

    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
    	boolean accountExists = false;
    	for (Account account : accounts) {
    		if (account.getHandle().equals(handle)) {
    			accountExists = true;
    		}
    	}
    	if (accountExists) {
    		throw new IllegalHandleException();
    	} else if (handle == "" || handle.length() > 30 || handle.contains(" ")){
    		throw new InvalidHandleException();
    	} else {
    		Account account = new Account(handle);
            accounts.add(account);
            return account.getId();	
    	}
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException{
    	boolean accountExists = false;
        for (int i=0; i<accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getId() == id) {
                accounts.remove(account);
                accountExists = true;
            }
        }
        if (!accountExists) {
        	throw new AccountIDNotRecognisedException();
        }
    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
    	boolean oldHandleExists = false;
        for (int i=0; i<accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getHandle() == oldHandle) {
            	oldHandleExists = true;
            	for (Account accountIterator : accounts) {
            		if (accountIterator.getHandle() == newHandle) {
            			throw new IllegalHandleException();
            		}
            	}
            	if (newHandle == "" || newHandle.length() > 30 || newHandle.contains(" ")){
            		throw new InvalidHandleException();
            	} else {
            		account.setHandle(newHandle);
            	}
            }
        }
        if (!oldHandleExists) {
        	throw new HandleNotRecognisedException();
        }
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
    	String user = null;
    	boolean accountExists = false;
    	for (Account account : accounts) {
    		if (account.getHandle() == handle) {
    			accountExists = true;
    			user = account.toString();
    		}
    	}
    	if (!accountExists) {
    		throw new HandleNotRecognisedException();
    	}
    	return user;
    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
    	boolean handleExists = false;
    	for (Account account : accounts) {
    		if (account.getHandle() == handle) {
    			handleExists = true;
    		}
    	}
    	if (!handleExists) {
    		throw new HandleNotRecognisedException();
    	} else if (message == "" || message.length() > 100) {
    		throw new InvalidPostException();
    	} else {
    		Post post = new Post(handle, message);
    		posts.add(post);
    		return post.getId();
    	}
        
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        boolean endorsementExists = false;
        boolean accountExists = false;
        boolean actionablePost = false;
        for (Account account : accounts) {
            if (account.getHandle() == handle) {
                accountExists = true;
            }
        }
        for (Post post : posts) {
            if (post.getId() == id && post instanceof Endorsement) {
                endorsementExists = true;
            }
        }
        for (Post post : posts) {
            // not sure if comments can be endorsed
            // todo check if comments can be endorsed
            if (post.getId() == id && (post instanceof Post || post instanceof Comment)) {
                actionablePost = true;
            }
        }
        if (!accountExists) {
            throw new HandleNotRecognisedException();
        } else if (endorsementExists) {
            throw new PostIDNotRecognisedException();
        } else if (actionablePost){
            throw new NotActionablePostException();
        } else {
            Endorsement endorsement = new Endorsement(handle, id);
            posts.add(endorsement);
            return endorsement.getId();
        }
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

        boolean commentExists = false;
        boolean accountExists = false;
        boolean actionablePost = false;
        for (Account account : accounts) {
            if (account.getHandle() == handle) {
                accountExists = true;
            }
        }
        for (Post post : posts) {
            if (post.getId() == id && post instanceof Comment) {
                commentExists = true;
            }
        }
        for (Post post : posts) {
            if (post.getId() == id && (post instanceof Post || post instanceof Comment)) {
                actionablePost = true;
            }
        }
        if (!accountExists) {
            throw new HandleNotRecognisedException();
        } else if (commentExists) {
            throw new PostIDNotRecognisedException();
        } else if (actionablePost){
            throw new NotActionablePostException();
        } else {
            Comment comment = new Comment(handle, id, message);
            posts.add(comment);
            return comment.getId();
        }
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        boolean postExists = false;
        for (Post post : posts) {
			if (post.getId() == id) {
				postExists = true;
				posts.remove(post);
			}
		}
        if (!postExists) {
        	throw new PostIDNotRecognisedException();
        }
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id)
            throws PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMostEndorsedPost() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMostEndorsedAccount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void erasePlatform() {
        this.accounts.clear();
        this.posts.clear();
        System.out.println("\nPlatform erased...");
    }

    @Override
    public void savePlatform(String filename) throws IOException {
    	ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
		outputStream.writeObject(this);
		outputStream.flush();
		outputStream.close();
		System.out.printf("\nPlatform saved in %s%n", filename);
    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        Object obj = in.readObject();
        if (obj instanceof SocialMedia) {
        	this.accounts = ((SocialMedia) obj).accounts;
        	this.posts = ((SocialMedia) obj).posts;
        }
        in.close();
        System.out.printf("\nPlatform loaded from %s%n", filename);
    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
    	boolean accountExists = false;
    	for (Account account : accounts) {
    		if (account.getHandle() == handle) {
    			accountExists = true;
    		}
    	}
    	if (accountExists) {
    		throw new IllegalHandleException();
    	} else if (handle == "" || handle.length() > 30 || handle.contains(" ")){
    		throw new InvalidHandleException();
    	} else {
    		Account account = new Account(handle, description);
            accounts.add(account);
            return account.getId();	
    	}
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
    	boolean accountExists = false;
        for (int i=0; i<accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getHandle() == handle) {
                accounts.remove(account);
                accountExists = true;
            }
        }
        if (!accountExists) {
        	throw new HandleNotRecognisedException();
        }
    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
    	boolean accountExists = false;
        for (int i=0; i<accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getHandle() == handle) {
                account.setDescription(description);
                accountExists = true;
            }
        }
        if (!accountExists) {
        	throw new HandleNotRecognisedException();
        }
    }

    @Override
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    @Override
    public int getTotalOriginalPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTotalCommentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }
    

    
    public static void main(String[] args) {
        //new SocialMedia
        SocialMedia a = new SocialMedia();

        try {
            a.createAccount("test1", "test");
            a.createPost("test1","test");
            a.createAccount("test2", "test");
            a.endorsePost("test2", 1);
            ArrayList<Post> posts = new ArrayList<>();
            posts = a.getPosts();

            for (Post post : posts){
                System.out.println(post.toString());
            }
        }
        catch (IllegalHandleException e) {
            e.printStackTrace();
        }
        catch (InvalidHandleException e) {
            e.printStackTrace();
        }
        catch (InvalidPostException e) {
            e.printStackTrace();
        }
        catch (HandleNotRecognisedException e){
            e.printStackTrace();
        }
        catch (PostIDNotRecognisedException e){
            e.printStackTrace();
        }
        catch (NotActionablePostException e){
            e.printStackTrace();
        }
    }
        /*
        //create accounts
        try {
			System.out.println(a.createAccount("user1"));
			System.out.println(a.createAccount("user2"));
		} catch (IllegalHandleException | InvalidHandleException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //print list of accounts and length
        System.out.println("\n\n"+a.getAccounts());
        System.out.println(a.getNumberOfAccounts()+ "\n\n");

        //show account
        try {
			System.out.println(a.showAccount("user1"));
		} catch (HandleNotRecognisedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //update description
        try {
			a.updateAccountDescription("user1", "user1Bio");
		} catch (HandleNotRecognisedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println();

        //show account
        try {
			System.out.println(a.showAccount("user1"));
		} catch (HandleNotRecognisedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println();

        //remove accounts
        try {
			a.removeAccount("user1");
		} catch (HandleNotRecognisedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //print list of accounts and length
        System.out.println(a.getAccounts());
        System.out.println(a.getNumberOfAccounts());

        //save platform
        try {
			a.savePlatform("platform.ser");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //loading platform into new social media
        SocialMedia b = new SocialMedia();
        try {
			b.loadPlatform("platform.ser");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(b.getAccounts());
        b.erasePlatform();
        System.out.println(b.getAccounts());
    }*/
}
