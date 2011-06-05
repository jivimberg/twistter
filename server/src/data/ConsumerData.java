package data;

public enum ConsumerData {
	
	CONSUMER_KEY ("1fJQ3sBPrFtXCPYQIgIW9w"),
	CONSUMER_SECRET("Y9ESDVow06aPd4v8uuP5mmVoEYAoq32QjhBPzz9u24");
	
	private String value;
	
	private ConsumerData(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
}
