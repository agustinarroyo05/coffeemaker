class Machine extends React.Component{
	
	constructor(props) {
		super(props);
		this.state = {selectedCoffee : null, selectedAggregates : [], display : 0, 
				stockCoffees : [], stockAggregates : []};
		
		this.handleCoffeeChange = this.handleCoffeeChange.bind(this);
		this.handleAggregateChange = this.handleAggregateChange.bind(this);
		this.calculatePrice = this.calculatePrice.bind(this);
		this.buy = this.buy.bind(this);
	}
	
	handleCoffeeChange(coffee){
		this.setState({'selectedCoffee' : coffee}, () => this.calculatePrice());
	}
	
	handleAggregateChange(aggregate){
		var isContained = false;
		var aggregates = this.state.selectedAggregates || [];
		function remove() {
			return aggregates.filter(function(value, index, arr){
				var areDiff =  value.name != aggregate.name;
				if (!areDiff){
					isContained = true;
				}
				return areDiff;
			});
		}
		aggregates = remove();
		if(!isContained){
			aggregates.push(aggregate)
		}
		this.setState({'selectedAggregates' : aggregates}, () => this.calculatePrice());
	}
	
	calculatePrice(){
		let price = 0;
		price += (this.state.selectedCoffee == null) ? 0 : this.state.selectedCoffee.price;
		this.state.selectedAggregates.forEach(a => {
			price+=a.price;
		})
		this.setState({display : price});
	}
	
	async componentDidMount(){
		try {
		      const response = await fetch(`coffeemaker/products`);
		      if (!response.ok) {
		        throw Error(response.statusText);
		      }
			  const data = await response.json();
			  let coffees =[], aggregates = [];
			  
			  data.forEach(e => {
				if (e['productFamily'] == 'COFFEE'){
					coffees.push(e);
				}
				else 
					aggregates.push(e);
			   });
			   this.setState({'stockCoffees' : coffees, 'stockAggregates' : aggregates});   
				
		    } catch (error) {
		      console.log(error);
		 }
	}

	buy(){
		var products = this.state.selectedAggregates.map(e => e.name);
		
		if (this.state.selectedCoffee == null){
			alert("You must select a coffee");
			return;
		}
		products.push(this.state.selectedCoffee.name);
		fetch('coffeemaker/products', {
		    method: 'POST',
		    headers: {
		      'Accept': 'application/json',
		      'Content-Type': 'application/json'
		    },
		    body: JSON.stringify(products)
		  }).then(function(response) {
			  if(response.ok) {
				  alert("Your Coffee will be prepared");
			  }
		  	  else {
				  alert('Your Coffee will not be prepared, reload to check stock');
			 }
		  });
	}
	
	render(){
		
		const coffeeItems = this.state.stockCoffees.map((cof) =>
			<Coffee name={cof.name} price={cof.price} onCoffeeChange={this.handleCoffeeChange} />
		);
		const aggregatedItems = this.state.stockAggregates.map((agg) =>
			<Aggregate name={agg.name} price={agg.price} onAggregateChange={this.handleAggregateChange}/>
		);
		
		return(<div>
			<ul className='list-group,table, ulwrap' >
				<li className='list-group-item'>
					Your Order: ${this.state.display}
				</li>
			</ul>
			<ul className='list-group,table, ulwrap'>{coffeeItems}</ul>;
			<ul className='list-group,table, ulwrap'>{aggregatedItems}</ul>
			<ul className='list-group,table, ulwrap' >
				<li className='list-group-item'> 	
					<button type="button" onClick={this.buy}>Buy</button>
				</li>
			</ul>
		</div>);
	}
	
}

class Coffee extends React.Component{
	
	constructor(props) {
		super(props);
		var coffee = {name : props.name, price : props.price};
		this.state = {coffee : coffee};
		this.handleChange = this.handleChange.bind(this);
	}
	
	handleChange(e) {
		var coffee = {name : e.target.getAttribute("value"), price : e.target.getAttribute("price") * 1};
		this.props.onCoffeeChange(coffee);
	 }

	render(){
		return(
			<li className='list-group-item'> 	
				<input type='radio' onChange={this.handleChange} id={this.state.coffee.name} name='coffee' 
					value={this.state.coffee.name} price={this.state.coffee.price}/>
					Coffee:  {this.state.coffee.name}   Price:  {this.state.coffee.price}
			 </li>		
		);
	}
}

class Aggregate extends React.Component{
	
	constructor(props) {
		super(props);
		var aggregate = {name : props.name, price : props.price};
		this.state = {aggregate : aggregate};
		this.handleChange = this.handleChange.bind(this);
	}
	
	handleChange(e) {
		var aggregate = {name : e.target.getAttribute('value'), price : e.target.getAttribute('price') * 1};
		this.props.onAggregateChange(aggregate);
	 }

	render(){
		return(
			<li className='list-group-item'> 	
				<input type='checkbox'  onChange={this.handleChange} id={this.state.aggregate.name} 
					name='aggregate' value={this.state.aggregate.name} price={this.state.aggregate.price}/>
					{this.state.aggregate.name}  Price:  {this.state.aggregate.price}
			 </li>		
		);
	}
}


ReactDOM.render(
  <Machine />,
  document.getElementById('root')
);