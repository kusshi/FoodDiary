package com.kusshi.springboot;

public enum State {
	
	INITIAL {
		@Override
		public State accept() {
			return WAIT_FOOD_NAME;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},

	WAIT_FOOD_NAME {
		@Override
		public State record() {
			return SET_FOOD_NAME;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},
	
	SET_FOOD_NAME {
		@Override
		public State record() {
			return WAIT_FOOD_CALORIE;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},
	
	WAIT_FOOD_CALORIE {
		@Override
		public State record() {
			return SET_FOOD_CALORIE;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	},
	
	SET_FOOD_CALORIE {
		@Override
		public State record() {
			return INITIAL;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	};

	public State accept() {
		throw new IllegalStateException();
	}
	
	public State record() {
		throw new IllegalStateException();
	}
	
	public State cancel() {
		throw new IllegalStateException();
	}

}
