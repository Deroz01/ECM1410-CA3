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
    
    public SocialMedia() {
		posts.add(new Post());
	}

	@Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
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
    	String user = "";
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
    	boolean handleExists = false;
    	boolean postExists = false;
    	boolean isEndorsement = false;
    	Post endorsement = null;
    	for (Account account : accounts) {
    		if (account.getHandle() == handle) {
    			handleExists = true;
    		}
    	}
    	for (Post post : posts) {
			if (post.getId()== id) {
				postExists = true;
				if (post instanceof Endorsement) {
					isEndorsement = true;
				} else {
					endorsement = new Endorsement(handle, post.getMessage(), post.getHandle());
					posts.add(endorsement);
					post.increaseEndorseNumber();
				}
				break;
			}
		}
    	if (!handleExists) {
    		throw new HandleNotRecognisedException();
    	} else if (!postExists) {
			throw new PostIDNotRecognisedException();
		} else if (isEndorsement) {
			throw new NotActionablePostException();
		}
        return endorsement.getId();
    }

   @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
    	boolean handleExists = false;
    	boolean postExists = false;
    	boolean isEndorsement = false;
    	boolean validPost = true;
    	Post comment = null;
    	int accountIndex = -1;
    	for (int i=0; i<accounts.size(); i++) {
    		Account account = accounts.get(i);
    		if (account.getHandle() == handle) {
    			handleExists = true;
    			accountIndex = i;
    		}
    	}
    	for (Post post : posts) {
		if (post.getId() == id && id>=0) {
			postExists = true;
			if (post instanceof Endorsement) {
				isEndorsement = true;
			} else if (message ==""|| message.length()>30){
				validPost = false;
			} 
			else {
				comment = new Comment(id, handle, message, post.getIndent());
				posts.add(comment);
				post.increaseCommentNumber();
				accounts.get(accountIndex).increasePostCount();
			}
			break;
		}
	}
    	if (!handleExists) {
    		throw new HandleNotRecognisedException();
    	} else if (!postExists) {
			throw new PostIDNotRecognisedException();
		} else if (isEndorsement) {
			throw new NotActionablePostException();
		} else if (!validPost) {
			throw new InvalidPostException();
		} else {
			return comment.getId();    
		}
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        boolean postExists = false;
        for (Post post : posts) {
		if (post.getId() == id) {
			postExists = true;
			for (Post childPost : posts) {
				if (childPost instanceof Endorsement) {
					if (((Endorsement) childPost).getEndorsedHandle() == post.getHandle()) {
						deletePost(childPost.getId());
					}
				} else if (childPost instanceof Comment) {
					if (((Comment) childPost).getOrginalPostId() == post.getId()) {
						((Comment) childPost).setOrginalPostId(-1);
					}
				}
			}
			posts.remove(post);
		}
	}
        if (!postExists) {
        	throw new PostIDNotRecognisedException();
        }
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
    	boolean postExists = false;
        String postString = "";
        for (Post post : posts) {
        	if (post.getId() == id) {
        		postString = post.toString();
        		postExists = true;
        	}
        }
        if (!postExists) {
        	throw new PostIDNotRecognisedException();
        }
        return postString;
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id)
            throws PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMostEndorsedPost() {
    	Post mostEndorsedPost = null;
    	int mostEndorsements = 0;
    	for (Post post : posts) {
			if (post.getEndorseNumber() >= mostEndorsements) {
				mostEndorsements = post.getEndorseNumber();
				mostEndorsedPost = post;
			}
		}
        return mostEndorsedPost.getId();
    }

    @Override
    public int getMostEndorsedAccount() {
    	Account mostEndorsedAccount = null;
    	int mostEndorsements = 0;
    	for (Account account : accounts) {
    		int accountEndorsements = 0;
			for (Post post : posts) {
				if (post.getHandle() == account.getHandle()) {
					accountEndorsements += post.getEndorseNumber();
				}
			}
			if (accountEndorsements >= mostEndorsements) {
				mostEndorsements = accountEndorsements;
				mostEndorsedAccount = account;
			}
		}
        return mostEndorsedAccount.getId();
    }

    @Override
    public void erasePlatform() {
        this.accounts.clear();
        this.posts.clear();
        Post.resetIdCounter();
        Account.resetIdCounter();
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
        	System.out.printf("\nPlatform loaded from %s%n", filename);
        }
        in.close();
        
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
        int totalOriginalPosts = 0;
        for (Post post : posts) {
        	if (!(post instanceof Endorsement || post instanceof Comment)) {
				totalOriginalPosts ++;
			}
        }
        return totalOriginalPosts;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        int totalEndorsements = 0;
        for (Post post : posts) {
			if (post instanceof Endorsement) {
				totalEndorsements++;
			}
		}
        return totalEndorsements;
    }

    @Override
    public int getTotalCommentPosts() {
    	int totalComments = 0;
        for (Post post : posts) {
			if (post instanceof Comment) {
				totalComments++;
			}
		}
        return totalComments;
    }
    
    
    
    
    
    public static void main(String[] args) {
    	//new SocialMedia
        SocialMedia a = new SocialMedia();
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
        */
    }
}
