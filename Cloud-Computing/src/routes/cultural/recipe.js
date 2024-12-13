const express = require('express');
const router = express.Router();
const {getRecipe,getRecipeDetail}=require('../../controllers/cultural/recipeController')

router.get('/',getRecipe);

router.get('/:id',getRecipeDetail);

module.exports=router
