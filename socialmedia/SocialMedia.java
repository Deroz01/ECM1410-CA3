package socialmedia;
import java.io.*;
import java.util.ArrayList;

import javax.security.auth.callback.ConfirmationCallback;

public class SocialMedia implements SocialMediaPlatform {
    ArrayList<Account> accounts = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();
    
    public ArrayList<Post> getPosts() {
		return posts;
	}
    public ArrayList<Account> getAccounts() {
		return accounts;
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
    	String handleString="";
        for (int i=0; i<accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getId() == id) {
                accounts.remove(account);
                accountExists = true;
                handleString = account.getHandle();
            }
        }
        if (!accountExists) {
        	throw new AccountIDNotRecognisedException();
        }
        for (int i=0; i<posts.size();i++) {
        	Post post = posts.get(i);
			if (post.getHandle() == handleString) {
				try {
					deletePost(post.getId());
				} catch (PostIDNotRecognisedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
    	int accountIndex = -1;
    	for (int i=0; i<accounts.size(); i++) {
    		Account account = accounts.get(i);
    		if (account.getHandle() == handle) {
    			handleExists = true;
    			accountIndex = i;
    		}
    	}
    	if (!handleExists) {
    		throw new HandleNotRecognisedException();
    	} else if (message == "" || message.length() > 100) {
    		throw new InvalidPostException();
    	} else {
    		Post post = new Post(handle, message);
    		posts.add(post);
    		accounts.get(accountIndex).increasePostCount();
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
    	int accountIndex = -1;
    	for (int i=0; i<accounts.size(); i++) {
    		Account account = accounts.get(i);
    		if (account.getHandle() == handle) {
    			handleExists = true;
    			accountIndex = i;
    		}
    	}
    	for (Post post : posts) {
			if (post.getId()== id && id>=0) {
				postExists = true;
				if (post instanceof Endorsement) {
					isEndorsement = true;
				} else {
					endorsement = new Endorsement(handle, post.getMessage(), post.getHandle());
					posts.add(endorsement);
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
					increaseCommentsAllParentPosts(comment);
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
        for (int i=0; i<posts.size();i++) {
        	Post post = posts.get(i);
			if (post.getId() == id && id>=0) {
				postExists = true;
				if (post.getCommentNumber()>0) {
					post.setMessage("The original content was removed from the system and is no longer available.");
				} else {
					posts.remove(post);
				}
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
        	if (post.getId() == id && id>=0) {
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
    public void increaseCommentsAllParentPosts (Post comment) {
    	for (Post post : posts) {
    		if (comment instanceof Comment && ((Comment)comment).getOrginalPostId() == post.getId()) {
    			increaseCommentsAllParentPosts(post);
    			post.increaseCommentNumber();
			}
    		
    	}
    }
    public static void main(String[] args) {
    	//new SocialMedia
        SocialMedia a = new SocialMedia();
        try {
			a.createAccount("user1");
			a.createAccount("user2");
			a.createAccount("user3");
			a.createAccount("user4");
			a.createAccount("user5");
		} catch (IllegalHandleException | InvalidHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			int firstPost = a.createPost("user1", "I like examples...");
			int secondPost = a.commentPost("user2", firstPost, "comment");
			int thirdPost = a.commentPost("user2", secondPost, "No more than me...");
			a.commentPost("user2", secondPost, "No more than me...");
			
		} catch (HandleNotRecognisedException | InvalidPostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PostIDNotRecognisedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotActionablePostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for (Post post: a.getPosts()) {
        	System.out.println(post);
        }
        
    }
}
