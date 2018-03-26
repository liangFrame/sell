package cn.lframe.sell.controller;

import cn.lframe.sell.VO.ResultVO;
import cn.lframe.sell.converter.OrderForm2OrderDTOConverter;
import cn.lframe.sell.dto.OrderDTO;
import cn.lframe.sell.enums.ResultEnum;
import cn.lframe.sell.exception.SellException;
import cn.lframe.sell.form.OrderForm;
import cn.lframe.sell.service.BuyerService;
import cn.lframe.sell.service.OrderService;
import cn.lframe.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author home-pc
 * @create2018 -03 -14 -14:33
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;
//    创建订单
//    ① 查看我们的创建订单API文档时，会发现该方法会返回一种我们已经定义好的数据格式ResultVO.
//       然后在它的data里面装的是一个Map类型的参数，所以就定义了我们如下方法的返回值。
//    ② 从创建订单API文档可以看出，我们往方法中传递参数的时候，需要传递那么多，所以我们将
//       新建一个类，用于满足我们方法的参数。
//    ③ BindingResult 表单验证
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
//        在service层，我们使用的是OrderDTO来创建订单的，而这里从前端传进来的是orderForm，
//        所以需要我们写一个类把orderForm转换为orderDTO,最后才能调用service层的create（）方法。

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
//    查看订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid ,
                                         @RequestParam(value = "page" , defaultValue = "0") Integer page,
                                         @RequestParam(value = "size" , defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage= orderService.findList(openid, request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }
//    订单详情

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid ,
                                     @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }
//    取消订单

    @PostMapping("/cancel")
    public ResultVO cancel (@RequestParam("openid") String openid ,
                            @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
